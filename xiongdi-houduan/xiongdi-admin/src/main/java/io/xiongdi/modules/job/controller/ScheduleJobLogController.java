package io.xiongdi.modules.job.controller;

import io.xiongdi.common.utils.PageUtils;
import io.xiongdi.common.utils.R;
import io.xiongdi.modules.job.entity.ScheduleJobLogEntity;
import io.xiongdi.modules.job.service.ScheduleJobLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 定时任务日志类
 * @author wujiaxing
 * @date 2019-08-02
 */
@RestController
@RequestMapping("/sys/scheduleLog")
public class ScheduleJobLogController {

    @Autowired
    private ScheduleJobLogService scheduleJobLogService;

    /**
     * 定时任务日志列表
     * @param params
     * @return
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:schedule:log")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = scheduleJobLogService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 定时任务日志信息
     * @param logId
     * @return
     */
    @RequestMapping("/info/{logId}")
    public R info(@PathVariable Long logId) {
        ScheduleJobLogEntity log = scheduleJobLogService.getById(logId);
        return R.ok().put("log", log);
    }
}
