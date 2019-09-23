package io.xiongdi.modules.job.utils;

import io.xiongdi.common.utils.DateUtils;
import io.xiongdi.common.utils.SpringContextUtils;
import io.xiongdi.modules.job.entity.ScheduleJobEntity;
import io.xiongdi.modules.job.entity.ScheduleJobLogEntity;
import io.xiongdi.modules.job.service.ScheduleJobLogService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.Method;

/**
 * 定时任务执行类
 * @author wujiaxing
 * @date 2019-07-28
 */
@Slf4j
public class ScheduleJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        // 获取到运行前放入的参数
        ScheduleJobEntity scheduleJob = (ScheduleJobEntity) context.getMergedJobDataMap().get(ScheduleJobEntity.JOB_PARAM_KEY);

        // 从 IOC 容器获取操作日志的 Bean
        ScheduleJobLogService scheduleJobLogService = (ScheduleJobLogService) SpringContextUtils.getBean("scheduleJobLogService");

        // 封装存入数据库的日志实体类
        ScheduleJobLogEntity scheduleJobLogEntity = new ScheduleJobLogEntity();
        scheduleJobLogEntity.setBeanName(scheduleJob.getBeanName());
        scheduleJobLogEntity.setParams(scheduleJob.getParams());
        scheduleJobLogEntity.setJobId(scheduleJob.getJobId());
        scheduleJobLogEntity.setCreateTime(DateUtils.now());
        // 任务开始时间
        long startTime = DateUtils.currentMillSeconds();
        try {
            log.debug("任务准备执行，任务ID：{}",scheduleJob.getJobId());
            // 获取任务的 Bean
            Object bean = SpringContextUtils.getBean(scheduleJob.getBeanName());
            // 获取目标方法
            Method targer = bean.getClass().getDeclaredMethod("run", String.class);
            // 执行目标方法
            targer.invoke(bean,scheduleJob.getParams());

            // 执行任务总时长
            long times = DateUtils.currentMillSeconds() - startTime;
            scheduleJobLogEntity.setTimes(times);
            // 执行状态 0 ：成功  1 ：失败
            scheduleJobLogEntity.setStatus(0);
            log.debug("任务执行完毕，任务ID：{}  总共耗时：{}毫秒", scheduleJob.getJobId(),times);
        } catch (Exception e) {
            log.error("任务执行失败，任务ID：{}，错误信息：{}",scheduleJob.getJobId(),e);
            // 执行任务总时长
            long times = DateUtils.currentMillSeconds() - startTime;
            scheduleJobLogEntity.setTimes(times);
            // 执行状态 0 ：成功  1 ：失败
            scheduleJobLogEntity.setStatus(1);
        }finally{
            scheduleJobLogService.save(scheduleJobLogEntity);
        }
    }
}
