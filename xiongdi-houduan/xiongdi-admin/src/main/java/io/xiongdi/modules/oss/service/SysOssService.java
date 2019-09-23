package io.xiongdi.modules.oss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.xiongdi.common.utils.PageUtils;
import io.xiongdi.modules.oss.entity.SysOssEntity;

import java.util.Map;

/**
 * 文件上传 service
 * @author wujiaxing
 * @date 2019-08-06
 */
public interface SysOssService extends IService<SysOssEntity> {

    PageUtils queryPage(Map<String, Object> params);
}
