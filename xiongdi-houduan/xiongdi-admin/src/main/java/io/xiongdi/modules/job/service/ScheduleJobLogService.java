package io.xiongdi.modules.job.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.xiongdi.common.utils.PageUtils;
import io.xiongdi.modules.job.entity.ScheduleJobLogEntity;

import java.util.Map;

/**
 * 定时任务日志 service
 * @author wujiaxing
 * @date 2019-07-27
 */
public interface ScheduleJobLogService extends IService<ScheduleJobLogEntity> {
    /**
     * 查询并分页
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);
}
