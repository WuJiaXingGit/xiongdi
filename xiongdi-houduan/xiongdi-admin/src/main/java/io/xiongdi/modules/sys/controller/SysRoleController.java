package io.xiongdi.modules.sys.controller;

import io.xiongdi.common.annotation.SysLog;
import io.xiongdi.common.utils.PageUtils;
import io.xiongdi.common.utils.R;
import io.xiongdi.common.validator.ValidatorUtils;
import io.xiongdi.modules.sys.entity.SysRoleEntity;
import io.xiongdi.modules.sys.service.SysRoleMenuService;
import io.xiongdi.modules.sys.service.SysRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 角色管理
 * @author wujiaxing
 * @date 2019-08-17
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 角色列表
     * @param params roleName必须
     * @return
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:role:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysRoleService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 所有角色列表
     * @return
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:role:select")
    public R select() {
        System.out.println("进入角色");
        List<SysRoleEntity> list = sysRoleService.list();

        return R.ok().put("list", list);
    }

    /**
     * 角色信息
     * @param roleId 角色ID
     * @return
     */
    @RequestMapping("/info/{roleId}")
    @RequiresPermissions("sys:role:info")
    public R info(@PathVariable("roleId") Long roleId) {
        SysRoleEntity role = sysRoleService.getById(roleId);

        // 角色对应的菜单
        List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
        role.setMenuIdList(menuIdList);

        return R.ok().put("role", role);
    }

    /**
     * 保存角色
     * @param role
     * @return
     */
    @SysLog("保存角色")
    @RequestMapping("/save")
    @RequiresPermissions("sys:role:save")
    public R save(@RequestBody SysRoleEntity role) {
        // 验证角色
        ValidatorUtils.validateEntity(role);

        sysRoleService.savaRole(role);

        return R.ok();
    }

    /**
     * 删除角色
     * @param roleIds
     * @return
     */
    @SysLog("删除角色")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:role:delete")
    public R delete(@RequestBody Long[] roleIds) {
        sysRoleService.deleteBatch(roleIds);

        return R.ok();
    }

    /**
     * 修改角色
     * @param role
     * @return
     */
    @SysLog("修改角色")
    @RequestMapping("/update")
    @RequiresPermissions("sys:role:update")
    public R update(@RequestBody SysRoleEntity role) {
        // 验证角色
        ValidatorUtils.validateEntity(role);

        sysRoleService.update(role);

        return R.ok();
    }
}
