package io.xiongdi.modules.sys.shiro;

import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

/**
 * shiro 权限标签
 * @author wujiaxing
 * @date 2019-08-24
 */
@Component
public class ShiroTag {

    /**
     * 是否拥有该权限
     * @param permission 权限标识
     * @return true：是    false：否
     */
    public boolean hasPermission(String permission) {
        Subject subject = ShiroUtils.getSubject();
        return subject != null && subject.isPermitted(permission);
    }
}
