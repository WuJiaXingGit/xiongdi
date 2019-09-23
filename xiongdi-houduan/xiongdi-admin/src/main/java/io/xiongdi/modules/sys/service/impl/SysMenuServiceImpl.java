package io.xiongdi.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.xiongdi.common.utils.Constant;
import io.xiongdi.modules.sys.dao.SysMenuDao;
import io.xiongdi.modules.sys.entity.SysMenuEntity;
import io.xiongdi.modules.sys.entity.SysRoleMenuEntity;
import io.xiongdi.modules.sys.service.SysMenuService;
import io.xiongdi.modules.sys.service.SysRoleMenuService;
import io.xiongdi.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单管理
 * @author wujiaxing
 * @date 2019-08-12
 */
@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenuEntity> implements SysMenuService {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Override
    public List<SysMenuEntity> queryListByParentId(Long parentId, List<Long> menuIdList) {
        List<SysMenuEntity> menuList = queryListByParentId(parentId);
        // 如果是系统用户，则返回所有根菜单列表
        if (menuIdList == null) {
            return menuList;
        }
        List<SysMenuEntity> userMenuList = new ArrayList<>();
        for (SysMenuEntity entity : menuList) {
            if (menuIdList.contains(entity.getMenuId())) {
                userMenuList.add(entity);
            }
        }
        return userMenuList;
    }

    @Override
    public List<SysMenuEntity> queryListByParentId(Long parentId) {
        return baseMapper.queryListParentId(parentId);
    }

    @Override
    public List<SysMenuEntity> queryNotButtonList() {
        return baseMapper.queryNotButtonList();
    }

    @Override
    public List<SysMenuEntity> queryUserMenuList(Long userId) {
        // 系统管理员拥有最高的权限
        if (userId == Constant.SUPER_ADMIN) {
            return getAllMenuList(null);
        }
        // 用户菜单列表
        List<Long> menuId = sysUserService.queryAllMenuId(userId);
        return getAllMenuList(menuId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long menuId) {
        // 删除菜单
        this.removeById(menuId);
        // 删除菜单与角色的关系
        sysRoleMenuService.remove(new QueryWrapper<SysRoleMenuEntity>().eq("menu_id", menuId));
    }

    /**
     * 设置所有菜单列表层级结构
     * @param menuIdList 当前用户下的所有菜单
     * @return
     */
    private List<SysMenuEntity> getAllMenuList(List<Long> menuIdList) {
        // 查询根菜单列表
        List<SysMenuEntity> menuList = queryListByParentId(0L, menuIdList);
        // 递归获取子菜单
        getMenuTreeList(menuList, menuIdList);

        return menuList;
    }

    /**
     * 递归
     * @param menuList 需要迭代的层级菜单，例如：第一次是menuId 为 0L 的几个菜单
     * @param menuIdList 当前用户下所有菜单
     * @return
     */
    private List<SysMenuEntity> getMenuTreeList(List<SysMenuEntity> menuList, List<Long> menuIdList) {
        List<SysMenuEntity> subMenuList = new ArrayList<>();
        // 循环根菜单
        for (SysMenuEntity menuEntity : menuList) {
            // 目录
            if (menuEntity.getType() == Constant.MenuType.CATALOG.getValue()) {
                menuEntity.setList(getMenuTreeList(queryListByParentId(menuEntity.getMenuId(), menuIdList),menuIdList));
            }
            subMenuList.add(menuEntity);
        }
        return subMenuList;
    }
}
