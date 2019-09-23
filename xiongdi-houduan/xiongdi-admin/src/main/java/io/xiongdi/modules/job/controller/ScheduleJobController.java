package io.xiongdi.modules.job.controller;

import io.xiongdi.common.annotation.SysLog;
import io.xiongdi.common.utils.PageUtils;
import io.xiongdi.common.utils.R;
import io.xiongdi.common.validator.ValidatorUtils;
import io.xiongdi.modules.job.entity.ScheduleJobEntity;
import io.xiongdi.modules.job.service.ScheduleJobService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 定时任务 controller
 * @author wujiaxing
 * @date 2019-08-02
 */
@RestController
@RequestMapping("/sys/schedule")
public class ScheduleJobController {

    @Autowired
    private ScheduleJobService scheduleJobService;

    /**
     * 定时任务列表
     * @param params
     * @return
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:schedule:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = scheduleJobService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 定时任务信息
     * @param jobId 定时任务 ID
     * @return
     */
    @RequestMapping("/info/{jobId}")
    @RequiresPermissions("sys:schedule:info")
    public R info(@PathVariable("jobId") Long jobId) {
        ScheduleJobEntity schedule = scheduleJobService.getById(jobId);
        return R.ok().put("schedule", schedule);
    }

    /**
     * 保存定时任务
     * @param scheduleJobEntity
     * @return
     */
    @SysLog("保存定时任务")
    @RequestMapping("/save")
    @RequiresPermissions("sys:schedule:save")
    public R save(@RequestBody ScheduleJobEntity scheduleJobEntity) {
        // 校验 Bean
        ValidatorUtils.validateEntity(scheduleJobEntity);
        scheduleJobService.saveJob(scheduleJobEntity);
        return R.ok();
    }

    /**
     * 更新定时任务
     * @param scheduleJobEntity
     * @return
     */
    @SysLog("更新定时任务")
    @RequestMapping("/update")
    @RequiresPermissions("sys:schedule:update")
    public R update(@RequestBody ScheduleJobEntity scheduleJobEntity) {
        // 校验 Bean
        ValidatorUtils.validateEntity(scheduleJobEntity);
        scheduleJobService.update(scheduleJobEntity);
        return R.ok();
    }

    /**
     * 移除定时任务
     * @param jobIds 任务 ID
     * @return
     */
    @SysLog("移除定时任务")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:schedule:delete")
    public R delete(@RequestBody  Long[] jobIds) {
        scheduleJobService.deleteBatch(jobIds);
        return R.ok();
    }

    /**
     * 执行定时任务
     * @param jobIds
     * @return
     */
    @SysLog("执行定时任务")
    @RequestMapping("/run")
    @RequiresPermissions("sys:schedule:run")
    public R run(@RequestBody Long[] jobIds) {
        scheduleJobService.run(jobIds);
        return R.ok();
    }

    /**
     * 暂停定时任务
     * @param jobIds
     * @return
     */
    @SysLog("暂停定时任务")
    @RequestMapping("/pause")
    @RequiresPermissions("sys:schedule:pause")
    public R pause(@RequestBody Long[] jobIds) {
        scheduleJobService.pause(jobIds);
        return R.ok();
    }

    /**
     * 恢复定时任务
     * @param jobIds
     * @return
     */
    @SysLog("恢复定时任务")
    @RequestMapping("/resume")
    @RequiresPermissions("sys:schedule:resume")
    public R resume(@RequestBody Long[] jobIds) {
        scheduleJobService.resume(jobIds);
        return R.ok();
    }
}
