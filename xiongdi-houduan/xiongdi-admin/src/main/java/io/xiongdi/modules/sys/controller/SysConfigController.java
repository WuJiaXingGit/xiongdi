package io.xiongdi.modules.sys.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.xiongdi.common.annotation.SysLog;
import io.xiongdi.common.utils.PageUtils;
import io.xiongdi.common.utils.R;
import io.xiongdi.common.validator.ValidatorUtils;
import io.xiongdi.modules.sys.entity.SysConfigEntity;
import io.xiongdi.modules.sys.service.SysConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 系统配置信息
 * @author wujiaxing
 * @date 2019-08-14
 */
@RestController
@RequestMapping("/sys/config")
public class SysConfigController {

    @Autowired
    private SysConfigService sysConfigService;

    /**
     * 所有配置列表
     * @param params 参数，paramKey必须
     * @return
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:config:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = this.sysConfigService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 根据ID，获取配置信息
     * @param id
     * @return
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:config:info")
    @ResponseBody
    public R info(@PathVariable("id") Long id) {
        SysConfigEntity config = this.sysConfigService.getById(id);
        return R.ok().put("config", config);
    }

    /**
     * 保存配置
     * @param config
     * @return
     */
    @SysLog("保存配置")
    @RequestMapping("/save")
    @RequiresPermissions("sys:config:save")
    public R save(SysConfigEntity config) {
        // 验证外部参数是否合法
        ValidatorUtils.validateEntity(config);

        this.sysConfigService.save(config);
        return R.ok();
    }

    /**
     * 修改配置
     * @param entity
     * @return
     */
    @SysLog("修改配置")
    @RequestMapping("/update")
    @RequiresPermissions("sys:config:update")
    public R update(SysConfigEntity entity) throws JsonProcessingException {
        // 验证外部参数是否合法
        ValidatorUtils.validateEntity(entity);

        this.sysConfigService.update(entity);
        return R.ok();
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @SysLog("删除配置")
    @RequestMapping("/delete")
    @RequiresPermissions(("sys:config:delete"))
    public R delete(@RequestBody Long[] ids) {
        this.sysConfigService.deleteBatch(ids);

        return R.ok();
    }
}
