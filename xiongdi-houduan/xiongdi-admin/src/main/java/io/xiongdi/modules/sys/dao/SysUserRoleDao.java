package io.xiongdi.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.xiongdi.modules.sys.entity.SysUserRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户和角色对应关系
 * @author wujiaxing
 * @date 2019-08-10
 */
@Mapper
public interface SysUserRoleDao extends BaseMapper<SysUserRoleEntity> {

    /**
     * 根据用户查询角色列表
     * @param userId 用户 ID
     * @return
     */
    List<Long> queryRoleIdList(@Param("userId") Long userId);

    /**
     * 根据角色 ID 数组，批量删除
     * @param roleIds
     * @return
     */
    int deleteBatch(@Param("roleIds") Long[] roleIds);
}
