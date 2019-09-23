package io.xiongdi.modules.job.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.xiongdi.modules.job.entity.ScheduleJobEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 定时任务 Mapper
 * @author wujiaxing
 * @date 2019-07-27
 */
@Mapper
public interface ScheduleJobDao extends BaseMapper<ScheduleJobEntity> {
    /**
     * 批量更新状态
     * @param map
     * @return
     */
    int updateBatch(Map<String, Object> map);
}
