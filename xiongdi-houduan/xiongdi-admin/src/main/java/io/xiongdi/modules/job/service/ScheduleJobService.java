package io.xiongdi.modules.job.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.xiongdi.common.utils.PageUtils;
import io.xiongdi.modules.job.entity.ScheduleJobEntity;

import java.util.Map;

/**
 * 定时任务 service
 * @author wujiaxing
 * @date 2019-07-27
 */
public interface ScheduleJobService extends IService<ScheduleJobEntity> {

    /**
     * 查询并分页
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 保存定时任务
     * @param scheduleJob
     */
    void saveJob(ScheduleJobEntity scheduleJob);

    /**
     * 更新定时任务
     * @param scheduleJob
     */
    void update(ScheduleJobEntity scheduleJob);

    /**
     * 批量删除定时任务
     * @param jobIds
     */
    void deleteBatch(Long[]jobIds);

    /**
     * 批量更新定时任务状态
     * @param jobIds
     * @param status
     * @return
     */
    int updateBatchStatus(Long[] jobIds, int status);

    /**
     * 立即执行
     * @param jobIds
     */
    void run(Long[] jobIds);

    /**
     * 暂停执行
     * @param jobIds
     */
    void pause(Long[] jobIds);

    /**
     * 恢复执行
     * @param jobIds
     */
    void resume(Long[] jobIds);
}
