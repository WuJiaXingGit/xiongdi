package io.xiongdi.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.xiongdi.modules.sys.entity.SysMenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单
 * @author wujiaxing
 * @date 2019-08-10
 */
@Mapper
public interface SysMenuDao extends BaseMapper<SysMenuEntity> {

    /**
     * 根据父菜单，查询子菜单
     */
    List<SysMenuEntity> queryListParentId(@Param("parentId") Long parentId);

    /**
     * 查询不包含按钮的菜单列表
     */
    List<SysMenuEntity> queryNotButtonList();
}
