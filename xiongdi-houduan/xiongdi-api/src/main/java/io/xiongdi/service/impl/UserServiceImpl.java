package io.xiongdi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.xiongdi.common.exception.XDException;
import io.xiongdi.common.utils.DateUtils;
import io.xiongdi.common.validator.Assert;
import io.xiongdi.dao.UserDao;
import io.xiongdi.entity.TokenEntity;
import io.xiongdi.entity.UserEntity;
import io.xiongdi.form.LoginForm;
import io.xiongdi.service.TokenService;
import io.xiongdi.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wujiaxing
 * @date 2019-07-06
 * <p>
 *     userService的实现类，继承mybatis plus的通用service实现类，泛型参数为
 *          M 为baseMapper的子接口
 *          T 为返回值封装类（实体）
 * </p>
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    @Autowired
    private TokenService tokenService;

    @Override
    public UserEntity queryByMobile(String mobile) {
        UserEntity userEntity = baseMapper.selectOne(new QueryWrapper<UserEntity>().eq("mobile", mobile));
        return userEntity;
    }

    @Override
    public Map<String, Object> login(LoginForm form) {
        // 根据手机号到数据库去查询
        UserEntity userEntity = queryByMobile(form.getMobile());
        // 验证是否为空
        Assert.isNull(userEntity, "手机号或密码错误");
        // 密码匹配
        if (!userEntity.getPassword().equals(DigestUtils.sha256Hex(form.getPassword()))) {
            throw new XDException("手机号或密码错误");
        }
        // 创建token
        TokenEntity tokenEntity = tokenService.createToken(userEntity.getUserId());
        Map<String,Object> map = new HashMap<>(2);
        map.put("token", tokenEntity.getToken());
        map.put("expire", DateUtils.toMilliSeconds(tokenEntity.getExpireTime()) - DateUtils.currentMillSeconds());
        map.put("code", 0);
        return map;
    }
}
