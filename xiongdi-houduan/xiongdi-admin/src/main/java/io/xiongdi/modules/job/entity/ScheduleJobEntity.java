package io.xiongdi.modules.job.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 定时任务实体类
 * @author wujiaxing
 * @date 2019-07-27
 */
@Data
@TableName("schedule_job")
public class ScheduleJobEntity implements Serializable {

    private static final long serialVersionUID = -1643720989561710705L;

    /**
     * 任务调度参数 key
     */
    public final static String JOB_PARAM_KEY = "JOB_PARAM_KEY";
    /**
     * 任务 ID
     */
    @TableId
    private long jobId;
    /**
     * bean 的名称
     */
    @NotBlank(message = "bean 的名称不能为空")
    private String beanName;
    /**
     * 参数
     */
    private String params;
    /**
     * cron 表达式
     */
    @NotBlank(message = "cron 表达式不能为空")
    private String cronExpression;
    /**
     * 任务状态
     */
    private int status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
}
