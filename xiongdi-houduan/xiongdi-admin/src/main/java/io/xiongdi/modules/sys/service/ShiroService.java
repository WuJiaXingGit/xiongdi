package io.xiongdi.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * @author wujiaxing
 * @date 2019-09-17
 */
public interface ShiroService {

    /**
     * 获取用户权限列表
     * @param userId
     * @return
     */
    Set<String> getUserPermissions(long userId);


}
