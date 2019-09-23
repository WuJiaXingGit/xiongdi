package io.xiongdi.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.xiongdi.modules.sys.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色管理
 * @author wujiaxing
 * @date 2019-08-10
 */
@Mapper
public interface SysRoleDao extends BaseMapper<SysRoleEntity> {
}
