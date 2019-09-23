package io.xiongdi.modules.job.utils;

import io.xiongdi.common.exception.XDException;
import io.xiongdi.common.utils.Constant;
import io.xiongdi.modules.job.entity.ScheduleJobEntity;
import org.quartz.*;

/**
 * 定时任务工具类
 * @author wujiaxing
 * @date 2019-07-28
 */
public class ScheduleUtils {
    /**
     * 任务名称
     */
    private final static String JOB_NAME = "TASK_";

    /**
     * 获取触发器的 key
     * @param jobId 任务 ID
     */
    public static TriggerKey getTriggerKey(long jobId) {
        return TriggerKey.triggerKey(JOB_NAME + jobId);
    }

    /**
     * 获取 job 的 key
     * @param jobId 任务 ID
     * @return
     */
    public static JobKey getJobKey(long jobId) {
        return JobKey.jobKey(JOB_NAME + jobId);
    }

    /**
     * 获取表达式触发器
     * @param scheduler
     * @param jobId
     * @return
     */
    public static CronTrigger getCronTrigger(Scheduler scheduler, long jobId) {
        try {
            return (CronTrigger)scheduler.getTrigger(getTriggerKey(jobId));
        } catch (SchedulerException e) {
            throw new XDException("获取定时任务 cronTrigger 出现异常", e);
        }
    }

    /**
     * 创建定时任务
     * @param scheduler 调度器
     * @param scheduleJob 定时任务实体类
     */
    public static void createScheduleJob(Scheduler scheduler, ScheduleJobEntity scheduleJob) {
        try {
            // 构建 job (任务)信息
            JobDetail jobdetail = JobBuilder.newJob(ScheduleJob.class).withIdentity(getJobKey(scheduleJob.getJobId())).build();

            // 表达式调度构建器
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());

            // 根据新的 cronExpression 构建 trigger
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(scheduleJob.getJobId())).withSchedule(cronScheduleBuilder).build();

            // 放入参数，运行时可以获取
            jobdetail.getJobDataMap().put(ScheduleJobEntity.JOB_PARAM_KEY, scheduleJob);

            // 准备任务
            scheduler.scheduleJob(jobdetail,cronTrigger);

            if (scheduleJob.getStatus() == Constant.ScheduleStatus.PAUSE.getValue()) {
                pauseJob(scheduler, scheduleJob.getJobId());
            }
        } catch (SchedulerException e) {
            throw new XDException("创建定时任务失败", e);
        }
    }

    /**
     * 更新定时任务
     * @param scheduler 任务调度器
     * @param scheduleJob 定时任务实体类
     */
    public static void updateScheduleJob(Scheduler scheduler, ScheduleJobEntity scheduleJob) {
        try {
            // 获取触发器 key
            TriggerKey triggerKey = getTriggerKey(scheduleJob.getJobId());

            // 构建表达式,不触发立即执行,等待下次Cron触发频率到达时刻开始按照Cron频率依次执行
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression()).withMisfireHandlingInstructionDoNothing();

            // 获取触发器对象
            CronTrigger trigger = getCronTrigger(scheduler, scheduleJob.getJobId());

            // 根据新的 cronExpression 对象构建触发器
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();

            // 放入参数，运行时可以获取
            trigger.getJobDataMap().put(ScheduleJobEntity.JOB_PARAM_KEY, scheduleJob);

            // 重新部署
            scheduler.rescheduleJob(triggerKey, trigger);

            if (scheduleJob.getStatus() == Constant.ScheduleStatus.PAUSE.getValue()) {
                pauseJob(scheduler, scheduleJob.getJobId());
            }
        } catch (SchedulerException e) {
            throw new XDException("更新定时任务失败", e);
        }
    }

    /**
     * 立即执行定时任务
     * @param scheduler 任务调度器
     * @param scheduleJob 定时任务实体类
     */
    public static void run(Scheduler scheduler, ScheduleJobEntity scheduleJob) {
        try {
            // 设置参数
            JobDataMap map = new JobDataMap();
            map.put(ScheduleJobEntity.JOB_PARAM_KEY, scheduleJob);
            scheduler.triggerJob(getJobKey(scheduleJob.getJobId()),map);
        } catch (SchedulerException e) {
            throw new XDException("立即执行定时任务失败", e);
        }
    }

    /**
     * 任务暂停
     * @param scheduler 任务调度器
     * @param jobId 任务 ID
     */
    public static void pauseJob(Scheduler scheduler, long jobId) {
        try {
            scheduler.pauseJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new XDException("定时任务暂停失败", e);
        }
    }

    /**
     * 恢复定时任务
     * @param scheduler 任务调度器
     * @param jobId 任务 ID
     */
    public static void resumeJob(Scheduler scheduler, long jobId) {
        try {
            scheduler.resumeJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new XDException("恢复定时任务失败", e);
        }
    }

    /**
     * 删除定时任务
     * @param scheduler 任务调度器
     * @param jobId 任务 ID
     */
    public static void deleteJob(Scheduler scheduler, long jobId) {
        try {
            scheduler.deleteJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new XDException("删除定时任务失败", e);
        }
    }

}
