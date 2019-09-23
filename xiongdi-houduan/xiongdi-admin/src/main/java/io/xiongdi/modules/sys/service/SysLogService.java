package io.xiongdi.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.xiongdi.common.utils.PageUtils;
import io.xiongdi.modules.sys.entity.SysLogEntity;

import java.util.Map;

/**
 * 系统日志
 * @author wujiaxing
 * @date 2019-08-10
 */
public interface SysLogService extends IService<SysLogEntity> {

    /**
     * 查询分页
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);
}
