package io.xiongdi.modules.sys.shiro;

import io.xiongdi.common.exception.XDException;
import io.xiongdi.modules.sys.entity.SysUserEntity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * shiro 的工具类
 * @author wujiaxing
 * @date 2019-08-09
 */
public class ShiroUtils {

    /**
     * 算法名称
     */
    public final static String hashAlgorithName = "SHA-256";

    /**
     * 迭代次数
     */
    public final static int hashIterations = 16;


    /**
     * 加密
     * @param password 密码
     * @param salt 盐
     * @return
     */
    public static String sha256(String password, String salt) {
        return new SimpleHash(hashAlgorithName, password, salt, hashIterations).toString();
    }

    /**
     * 获取 session
     * @return shiro 管理的 session
     */
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    /**
     * Subject 是shiro 的主角，一个访问用户就是一个subject
     * @return 用户对象
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 获取当前用户信息
     * @return
     */
    public static SysUserEntity getUserEntity() {
        return (SysUserEntity)getSubject().getPrincipal();
    }

    /**
     * 获取用户ID
     * @return
     */
    public static Long getUserId() {
        return getUserEntity().getUserId();
    }

    /**
     * 设置作用域的值
     * @param key
     * @param value
     */
    public static void setAttribute(Object key, Object value) {
        getSession().setAttribute(key, value);
    }

    /**
     * 获取作用域的值
     * @param key
     * @return
     */
    public static Object getAttribute(Object key) {
        return getSession().getAttribute(key);
    }

    /**
     * 是否登录
     * @return
     */
    public static Boolean isLogin() {
        return getSubject().getPrincipal() != null;
    }

    /**
     * 登出
     */
    public static void logout() {
        getSubject().logout();
    }

    /**
     * 获取验证码
     * @param key
     * @return
     */
    public static String getKaptcha(String key) {
        Object kaptcha = getAttribute(key);
        if (kaptcha == null) {
            throw new XDException("验证码已失效");
        }
        getSession().removeAttribute(key);
        return kaptcha.toString();
    }
}


