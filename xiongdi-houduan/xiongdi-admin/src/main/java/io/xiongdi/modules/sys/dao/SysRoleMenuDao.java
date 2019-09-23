package io.xiongdi.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.xiongdi.modules.sys.entity.SysRoleMenuEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色与菜单关系
 * @author wujiaxing
 * @date 2019-08-10
 */
@Mapper
public interface SysRoleMenuDao extends BaseMapper<SysRoleMenuEntity> {

    /**
     * 根据角色 ID，查询菜单 ID 列表
     * @param roleId 角色 ID
     * @return
     */
    List<Long> queryMenuIdList(Long roleId);

    /**
     * 根据角色 ID 数组，批量删除
     * @param roleIds
     * @return
     */
    int deleteBatch(Long[] roleIds);
}
