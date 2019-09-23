package io.xiongdi.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.xiongdi.modules.sys.entity.SysUserRoleEntity;

import java.util.List;

/**
 * 用户与角色对应关系
 * @author wujiaxing
 * @date 2019-08-10
 */
public interface SysUserRoleService extends IService<SysUserRoleEntity> {

    /**
     * 保存或修改
     * @param userId
     * @param roleIdList
     */
    void saveOrUpdate(Long userId, List<Long> roleIdList);

    /**
     * 根据用户ID，获取角色ID列表
     * @param userId 用户ID
     * @return
     */
    List<Long> queryRoleIdList(Long userId);

    /**
     * 根据角色ID数组，批量删除
     * @param roleIds
     */
    int deleteBatch(Long[] roleIds);
}
