package io.xiongdi.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.xiongdi.common.utils.PageUtils;
import io.xiongdi.modules.sys.entity.SysDictEntity;

import java.util.Map;

/**
 * 数据字典
 * @author wujiaxing
 * @date 2019-08-10
 */
public interface SysDictService extends IService<SysDictEntity> {

    /**
     * 查询分页
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);
}
