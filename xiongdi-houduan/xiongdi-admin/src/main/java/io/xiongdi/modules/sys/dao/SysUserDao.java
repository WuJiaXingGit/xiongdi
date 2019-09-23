package io.xiongdi.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.xiongdi.modules.sys.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户管理
 * @author wujiaxing
 * @date 2019-08-10
 */
@Mapper
public interface SysUserDao extends BaseMapper<SysUserEntity> {

    /**
     * 查询指定用户的所有权限
     * @param userId 用户 ID
     * @return 所有权限信息
     */
    List<String> queryAllPerms(@Param("userId") Long userId);

    /**
     * 查询指定用户的所有菜单 ID
     * @param userId 用户 ID
     * @return 所有菜单 ID
     */
    List<Long> queryAllMenuId(@Param("userId") Long userId);
}
