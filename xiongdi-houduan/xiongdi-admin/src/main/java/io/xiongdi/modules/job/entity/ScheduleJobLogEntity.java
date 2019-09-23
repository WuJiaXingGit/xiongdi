package io.xiongdi.modules.job.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 定时任务日志
 * @author wujiaxing
 * @date 2019-07-27
 */
@Data
@TableName("schedule_job_log")
public class ScheduleJobLogEntity implements Serializable {
    private static final long serialVersionUID = 4680937822136407802L;
    /**
     * 日志 ID
     */
    @TableId
    private long logId;
    /**
     * 任务 ID
     */
    private long jobId;
    /**
     * bean 的名称
     */
    private String beanName;
    /**
     * 参数
     */
    private String params;
    /**
     * 任务状态    0：成功   1：失败
     */
    private int status;
    /**
     * 错误信息
     */
    private String error;
    /**
     * 耗时（单位：毫秒）
     */
    private long times;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
}
