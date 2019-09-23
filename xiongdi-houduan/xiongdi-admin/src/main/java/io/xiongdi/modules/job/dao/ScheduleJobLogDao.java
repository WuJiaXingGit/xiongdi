package io.xiongdi.modules.job.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.xiongdi.modules.job.entity.ScheduleJobLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 任务日志 Mapper
 * @author wujiaxing
 * @date 2019-08-25
 */
@Mapper
public interface ScheduleJobLogDao extends BaseMapper<ScheduleJobLogEntity> {
}
