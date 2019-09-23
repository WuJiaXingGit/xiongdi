package io.xiongdi.modules.job.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * 定时任务配置
 * @author wujiaxing
 * @date 2019-08-02
 */
@Configuration
public class ScheduleConfig {

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        /**
         * 设置数据库源，集群需要用到数据库
         */
        schedulerFactoryBean.setDataSource(dataSource);

        /**
         * 设置 quartz 的配置文件信息，和 spring 整合后在此处设置
         */
        Properties properties = new Properties();
        /**
         * 调度器名，无关紧要，名字随意定
         */
        properties.put("org.quartz.scheduler.instanceName", "XDSchedulerName");
        properties.put("org.quartz.scheduler.instanceId", "AUTO");
        /**
         * 线程池配置
         */
        properties.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        properties.put("org.quartz.threadPool.threadCount", "20");
        properties.put("org.quartz.threadPool.threadPriority", "5");
        /**
         * JobStore 配置，在这里自己管理事务
         */
        properties.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
        /**
         * 集群配置
         */
        properties.put("org.quartz.jobStore.isClustered", "true");
        properties.put("org.quartz.jobStore.clusterCheckinInterval", "15000");
        properties.put("org.quartz.jobStore.maxMisfiresToHandleAtATime", "1");
        properties.put("org.quartz.jobStore.misfireThreshold", "12000");
        properties.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
        properties.put("org.quartz.jobStore.selectWithLockSQL", "SELECT * FROM {0}LOCKS UPDLOCK WHERE LOCK_NAME = ?");
        schedulerFactoryBean.setQuartzProperties(properties);

        /**
         * 设置调度器名字
         */
        schedulerFactoryBean.setSchedulerName("XDScheduler");

        /**
         * 延时启动
         */
        schedulerFactoryBean.setStartupDelay(30);
        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContextKey");

        /**
         * 可选，QuartzScheduler 启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了
         */
        schedulerFactoryBean.setOverwriteExistingJobs(true);

        /**
         * 设置自动启动，默认为true
         */
        schedulerFactoryBean.setAutoStartup(true);


        return schedulerFactoryBean;
    }
}
