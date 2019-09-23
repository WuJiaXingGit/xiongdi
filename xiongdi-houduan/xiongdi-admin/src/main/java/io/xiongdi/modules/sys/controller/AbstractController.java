package io.xiongdi.modules.sys.controller;

import io.xiongdi.modules.sys.entity.SysUserEntity;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller公共组件
 * @author wujiaxing
 * @date 2019-08-15
 */
public abstract class AbstractController {

    protected  final  Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 获取当前用户
     * @return
     */
    protected SysUserEntity getUser() {
        return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
    }

    protected Long getUserId() {
        return getUser().getUserId();
    }

}
