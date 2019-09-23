package io.xiongdi.modules.sys.controller;

import io.xiongdi.common.utils.PageUtils;
import io.xiongdi.common.utils.R;
import io.xiongdi.common.validator.ValidatorUtils;
import io.xiongdi.modules.sys.entity.SysDictEntity;
import io.xiongdi.modules.sys.service.SysDictService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 数据字典
 * @author wujiaxing
 * @date 2019-08-15
 */
@RestController
@RequestMapping("/sys/dict")
public class SysDictController {

    @Autowired
    private SysDictService sysDictService;

    /**
     * 列表
     * @param params name必须
     * @return
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:dict:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysDictService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 查询单条数据
     * @param id
     * @return
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:dict:info")
    public R info(@PathVariable("id") Long id) {
        SysDictEntity dict = this.sysDictService.getById(id);
        return R.ok().put("dict", dict);
    }

    /**
     * 保存
     * @param sysDictEntity
     * @return
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:dict:save")
    public R save(@RequestBody SysDictEntity sysDictEntity) {
        // 校验类型
        ValidatorUtils.validateEntity(sysDictEntity);

        this.sysDictService.save(sysDictEntity);

        return R.ok();
    }

    /**
     * 修改
     * @param sysDictEntity
     * @return
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:dict:update")
    public R update(@RequestBody SysDictEntity sysDictEntity) {
        ValidatorUtils.validateEntity(sysDictEntity);

        this.sysDictService.updateById(sysDictEntity);

        return R.ok();
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:dict:delete")
    public R delete(@RequestBody Long[] ids) {
        this.sysDictService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
}
