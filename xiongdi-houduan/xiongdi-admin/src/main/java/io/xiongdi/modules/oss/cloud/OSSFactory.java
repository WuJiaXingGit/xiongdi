package io.xiongdi.modules.oss.cloud;

import io.xiongdi.common.utils.ConfigConstant;
import io.xiongdi.common.utils.Constant;
import io.xiongdi.common.utils.SpringContextUtils;
import io.xiongdi.modules.sys.service.SysConfigService;

import java.io.IOException;

/**
 * 策略上下文类，生产不同的策略
 * @author wujiaxing
 * @date 2019-08-05
 */
public final class OSSFactory {

    private static SysConfigService sysConfigService;

    static{
        OSSFactory.sysConfigService = (SysConfigService) SpringContextUtils.getBean("sysConfigService");
    }

    public static CloudStorageService build() throws IOException {
        // 从redis或数据库获取云存储配置信息
        CloudStorageConfig config = sysConfigService.getConfigObject(ConfigConstant.CLOUD_STORAGE_CONFIG_KEY, CloudStorageConfig.class);

        if (config.getType() == Constant.CloudService.QINIU.getValue()) {
            return new QiniuCloudStorageService(config);
        } else if (config.getType() == Constant.CloudService.ALIYUN.getValue()) {
            return new AliyunCloudStorageService(config);
        } else if (config.getType() == Constant.CloudService.QCLOUD.getValue()) {
            return new QcloudStorageService(config);
        }

        return null;
    }

}
