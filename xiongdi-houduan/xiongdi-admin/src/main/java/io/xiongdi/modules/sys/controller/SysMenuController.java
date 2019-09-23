package io.xiongdi.modules.sys.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.xiongdi.common.annotation.SysLog;
import io.xiongdi.common.exception.XDException;
import io.xiongdi.common.utils.Constant;
import io.xiongdi.common.utils.JsonUtils;
import io.xiongdi.common.utils.R;
import io.xiongdi.modules.sys.entity.SysMenuEntity;
import io.xiongdi.modules.sys.service.ShiroService;
import io.xiongdi.modules.sys.service.SysMenuService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 菜单管理
 * @author wujiaxing
 * @date 2019-08-17
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends AbstractController {

    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private ShiroService shiroService;


    /**
     * 导航菜单
     * @return
     */
    @RequestMapping("/nav")
    public R nav() {
        System.out.println("進入nav");

        // 查询用户下的所有菜单
        List<SysMenuEntity> menuList = sysMenuService.queryUserMenuList(getUserId());
        // 用户的所有权限
        Set<String> permissions = shiroService.getUserPermissions(getUserId());
        System.out.println(JSON.toJSONString(permissions));

        return R.ok().put("menuList", menuList).put("permissions", permissions);
    }

    /**
     * 所有菜单列表
     * @return
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:menu:list")
    public List<SysMenuEntity> list() {
        List<SysMenuEntity> menuList = sysMenuService.list();
        // 查询是否存在父菜单
        for (SysMenuEntity menu : menuList) {
            SysMenuEntity parentMenu = sysMenuService.getById(menu.getParentId());
            if (parentMenu != null) {
                menu.setParentName(parentMenu.getName());
            }
        }
        return menuList;
    }

    /**
     * 选择菜单（添加、修改菜单）
     * @return
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:menu:select")
    public R select() {
       // 查询列表数据
        List<SysMenuEntity> menuList = sysMenuService.queryNotButtonList();

        // 添加顶级菜单
        SysMenuEntity root = new SysMenuEntity();
        root.setMenuId(0L);
        root.setName("一级菜单");
        root.setParentId(-1L);
        root.setOpen(true);

        menuList.add(root);

        return R.ok().put("menuList", menuList);
    }

    /**
     * 菜单信息
     * @param menuId 菜单ID
     * @return
     */
    @RequestMapping("/info/{menuId}")
    @RequiresPermissions("sys:menu:info")
    public R info(@PathVariable("menuId") Long menuId) {
        SysMenuEntity menu = sysMenuService.getById(menuId);

        return R.ok().put("menu", menu);
    }

    /**
     * 保存菜单
     * @param menu
     * @return
     */
    @SysLog("保存菜单")
    @RequestMapping("/save")
    @RequiresPermissions("sys:menu:save")
    public R save(@RequestBody SysMenuEntity menu) {
        // 数据校验
        verifyForm(menu);

        sysMenuService.save(menu);

        return R.ok();
    }

    /**
     * 删除菜单
     * @param menuId
     * @return
     */
    @SysLog("删除菜单")
    @RequestMapping("/delete/{menuId}")
    @RequiresPermissions("sys:menu:delete")
    public R delete(@PathVariable long menuId) {
        if (menuId <= 33) {
            return R.error("系统菜单，不能删除");
        }

        // 判断是否有子菜单或按钮
        List<SysMenuEntity> menuList = sysMenuService.queryListByParentId(menuId);
        if (menuList.size() > 0) {
            return R.error("请先删除子菜单或按钮");
        }

        sysMenuService.delete(menuId);

        return R.ok();
    }

    /**
     * 修改菜单
     * @param menu
     * @return
     */
    @SysLog("修改菜单")
    @RequestMapping("/update")
    @RequiresPermissions("sys:menu:update")
    public R update(@RequestBody SysMenuEntity menu) {
        // 数据校验
        verifyForm(menu);

        sysMenuService.updateById(menu);

        return R.ok();
    }

    /**
     * 验证参数是否正确
     * @param menu
     */
    private void verifyForm(SysMenuEntity menu) {
       if (StringUtils.isBlank(menu.getName())) {
           throw new XDException("菜单名称不能为空");
       }

       if (menu.getParentId() == null) {
           throw new XDException("上级菜单不能为空");
       }

       // 菜单
       if (menu.getType() == Constant.MenuType.MENU.getValue()) {
           if (StringUtils.isBlank(menu.getUrl())) {
               throw new XDException("菜单URL不能为空");
           }
       }

       // 上级菜单类型
        int parentType = Constant.MenuType.CATALOG.getValue();
       // 获取非一级菜单的上级菜单
       if (menu.getParentId() != 0) {
           SysMenuEntity parentMenu = sysMenuService.getById(menu.getParentId());
           parentType = parentMenu.getType();
       }

       // 当前菜单如果目录、菜单，那么父节点必须为目录
        if (menu.getType() == Constant.MenuType.CATALOG.getValue() || menu.getType() == Constant.MenuType.MENU.getValue()) {
            if (parentType != Constant.MenuType.CATALOG.getValue()) {
                throw new XDException("上级菜单只能为目录类型");
            }
            return;
        }

        // 如果当前菜单为按钮，那么父节点必须为菜单
        if (menu.getType() == Constant.MenuType.BUTTON.getValue()) {
            if (parentType != Constant.MenuType.MENU.getValue()) {
                throw new XDException("上级菜单只能为菜单类型");
            }
            return;
        }
    }
}
