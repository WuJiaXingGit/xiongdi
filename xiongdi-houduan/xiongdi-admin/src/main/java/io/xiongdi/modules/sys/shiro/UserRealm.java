package io.xiongdi.modules.sys.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.xiongdi.common.utils.DateUtils;
import io.xiongdi.modules.sys.dao.SysUserDao;
import io.xiongdi.modules.sys.entity.SysUserEntity;
import io.xiongdi.modules.sys.entity.TokenEntity;
import io.xiongdi.modules.sys.service.ShiroService;
import io.xiongdi.modules.sys.service.TokenService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 验证和授权的场所（可以设置多个）
 * @author wujiaxing
 * @date 2019-08-08
 */
@Component
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private ShiroService shiroService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private SysUserDao sysUserDao;


    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof Auth2Token;
    }

    /**
     * 授权(验证权限时调用),此Realm只添加了权限，没有角色
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("进入了授权");
        SysUserEntity user = (SysUserEntity) principals.getPrimaryPrincipal();
        Long userId = user.getUserId();

        // 用户权限列表
        Set<String> permsSet = shiroService.getUserPermissions(userId);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);

        return info;
    }




    /**
     * 验证（登录时调用）
     * @param authToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
        System.out.println("进入认证");

        String token = (String) authToken.getCredentials();

        // 查询token信息
        TokenEntity tokenEntity = tokenService.queryByToken(token);

        // token不存在
        if (tokenEntity == null) {
            throw new IncorrectCredentialsException("凭证异常");
        }

        // token 过期
        if (tokenEntity.getExpireTime().isBefore(DateUtils.now())) {
            throw new ExpiredCredentialsException("token已失效，请重新登录");
        }
        // 查询用户信息
        SysUserEntity user = sysUserDao.selectOne(new QueryWrapper<SysUserEntity>().eq("user_id", tokenEntity.getUserId()));

        // 账号不存在
        if (user == null) {
            throw new UnknownAccountException("账号异常");
        }

        // 账号被锁定
        if (user.getStatus() == 0) {
            throw new LockedAccountException("账号被锁定，请联系管理员");
        }

        /**
         * 第一个参数传的什么对象，在认证方法里的getPrimaryPrincipal()就返回什么对象
         * 第二个参数就是要和此方法参数对象 authToken.getCredentials() 去比较，一般为密码或token
         */
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, tokenEntity.getToken(), getName());

        return info;
    }


    /**
     * 密码匹配器（设置加密算法和迭代次数）
     * <p>
     *     用三种验证器 AllowAllCredentialsMatcher(默认)、
     *     PasswordMatcher和 SimpleCredentialsMatcher(推荐)，
     *     因为它有各种算法的实现
     * </p>
     * @param credentialsMatcher
     */
   /* @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {   此处使用token登录，所以注释，如果密码，则需要指定
        // 使用 HashedCredentialsMatcher 匹配器
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        // 设置使用算法
        matcher.setHashAlgorithmName(ShiroUtils.hashAlgorithName);
        // 设置迭代次数
        matcher.setHashIterations(ShiroUtils.hashIterations);
        // 覆盖默认匹配器
        super.setCredentialsMatcher(matcher);
    }*/
}
