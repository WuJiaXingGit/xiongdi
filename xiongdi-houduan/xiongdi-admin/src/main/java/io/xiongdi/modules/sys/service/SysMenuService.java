package io.xiongdi.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.xiongdi.modules.sys.entity.SysMenuEntity;

import java.util.List;

/**
 * 系统菜单
 * @author wujiaxing
 * @date 2019-08-10
 */
public interface SysMenuService extends IService<SysMenuEntity> {

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     * @param menuIdList 用户菜单ID
     * @return
     */
    List<SysMenuEntity> queryListByParentId(Long parentId, List<Long> menuIdList);

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     * @return
     */
    List<SysMenuEntity> queryListByParentId(Long parentId);

    /**
     * 获取不包含按钮的菜单列表
     * @return
     */
    List<SysMenuEntity> queryNotButtonList();

    /**
     * 获取用户菜单列表
     * @param userId
     * @return
     */
    List<SysMenuEntity> queryUserMenuList(Long userId);

    /**
     * 删除
     * @param menuId
     */
    void delete(Long menuId);
}
