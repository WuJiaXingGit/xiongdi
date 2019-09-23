package io.xiongdi.modules.job.task;

/**
 * 定时任务接口
 * 所有定时任务都必须实现此接口
 * @author wujiaxing
 * @date 2019-08-04
 */
public interface ITask {

    /**
     * 执行定时任务的接口
     * @param params 多参数使用 JSON 的方式
     */
    void run(String params);
}
