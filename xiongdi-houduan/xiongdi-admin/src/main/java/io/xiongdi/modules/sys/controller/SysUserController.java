package io.xiongdi.modules.sys.controller;

import io.xiongdi.common.annotation.SysLog;
import io.xiongdi.common.utils.PageUtils;
import io.xiongdi.common.utils.R;
import io.xiongdi.common.utils.ResultType;
import io.xiongdi.common.validator.Assert;
import io.xiongdi.common.validator.ValidatorUtils;
import io.xiongdi.common.validator.group.AddGroup;
import io.xiongdi.common.validator.group.UpdateGroup;
import io.xiongdi.modules.sys.entity.SysUserEntity;
import io.xiongdi.modules.sys.service.SysUserRoleService;
import io.xiongdi.modules.sys.service.SysUserService;
import io.xiongdi.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * @author wujiaxing
 * @date 2019-08-16
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 所有用户列表
     * @param params username必须
     * @return
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:user:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysUserService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 获取登录用户信息
     * @return
     */
    @RequestMapping("/info")
    public R info() {
        return R.ok().put("user", getUser());
    }

    /**
     * 修改登录用户密码
     * @param password 原密码
     * @param newPassword 新密码
     * @return
     */
    @SysLog("修改密码")
    @RequestMapping("/password")
    public R password(String password, String newPassword) {
        // 验证密码
        Assert.isNull(newPassword, "新密码不能为空");

        //原密码
        password = ShiroUtils.sha256(password, getUser().getSalt());
        // 新密码
        newPassword = ShiroUtils.sha256(newPassword, getUser().getSalt());

        // 执行修改
        boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
        if (!flag) {
            return R.error(ResultType.PASSWORD_ERROR);
        }
        return R.ok();
    }

    /**
     * 用户信息
     * @param userId 用户ID
     * @return
     */
    @RequestMapping("/info/{userId}")
    @RequiresPermissions("sys:user:info")
    public R info (@PathVariable("userId") Long userId) {
        SysUserEntity user = sysUserService.getById(userId);

        // 获取用户所属的角色列表
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);

        return R.ok().put("user", user);
    }

    /**
     * 保存用户
     * @param entity
     * @return
     */
    @SysLog("保存用户")
    @RequestMapping("/save")
    @RequiresPermissions("sys:user:save")
    public R save(@RequestBody  SysUserEntity entity) {
        // 验证用户
        ValidatorUtils.validateEntity(entity, AddGroup.class);

        sysUserService.saveUser(entity);

        return R.ok();
    }

    /**
     * 修改用户
     * @param entity
     * @return
     */
    @SysLog("修改用户")
    @RequestMapping("/update")
    @RequiresPermissions("sys:user:update")
    public R update(@RequestBody SysUserEntity entity) {
        // 验证用户
        ValidatorUtils.validateEntity(entity, UpdateGroup.class);

        sysUserService.update(entity);

        return R.ok();
    }

    /**
     * 删除用户
     * @param ids
     * @return
     */
    @SysLog("删除用户")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public R delete(@RequestBody  Long[] ids) {
        // 判断是否为系统用户
        if (ArrayUtils.contains(ids, 1L)) {
            return R.error("系统用户不能删除");
        }

        // 当前操作用户不能删除
        if (ArrayUtils.contains(ids, getUserId())) {
            return R.error("当前用户不能删除");
        }

        sysUserService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
}
