package io.xiongdi.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.xiongdi.common.utils.PageUtils;
import io.xiongdi.modules.sys.entity.SysRoleEntity;

import java.util.Map;

/**
 * 角色管理
 * @author  wujiaxing
 * @date 2019-08-10
 */
public interface SysRoleService extends IService<SysRoleEntity> {

    /**
     * 查询分页
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 保存角色
     * @param role
     */
    void savaRole(SysRoleEntity role);

    /**
     * 修改角色
     * @param role
     */
    void update(SysRoleEntity role);

    /**
     * 批量删除
     * @param roleIds
     */
    void deleteBatch(Long[] roleIds);
}
