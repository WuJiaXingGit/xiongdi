package io.xiongdi.modules.oss.cloud;

import io.xiongdi.common.utils.DateUtils;
import io.xiongdi.common.utils.FormatPattern;
import org.apache.commons.lang.StringUtils;

import java.io.InputStream;
import java.util.UUID;

/**
 * 云存储服务(七牛云，阿里云，腾讯云，又拍云)，策略模式
 * @author wujiaxing
 * @date 2019-08-04
 */
public abstract class CloudStorageService {
    /**
     * 云存储配置
     */
    protected CloudStorageConfig config;

    /**
     * 文件路径
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 返回上传路径
     */
    public String getPath(String prefix, String suffix) {
        // 获取 UUID
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        // 文件路径
        String path = DateUtils.dateToString(DateUtils.now(), FormatPattern.DATE_FMT_0.pattern()) + "/" + uuid;

        if (StringUtils.isNotBlank(prefix)) {
            path = prefix + "/" + path;
        }
        return path + suffix;
    }

    /**
     * 文件上传
     * @param bytes 文件字节数组
     * @param path 文件路径，包含文件名
     * @return 返回 http 地址
     */
    public abstract String upload(byte[] bytes, String path) ;

    /**
     * 文件上传
     * @param bytes 文件字节数组
     * @param suffix 后缀
     * @return 返回 http 地址
     */
    public abstract String uploadSuffix(byte[] bytes, String suffix);

    /**
     * 文件上传
     * @param inputStream  字节流
     * @param path 文件路径，包含文件名
     * @return 返回 http 路径
     */
    public abstract String upload(InputStream inputStream, String path);

    /**
     * 文件上传
     * @param inputStream 字节流
     * @param suffix 后缀
     * @return 返回 http 路径
     */
    public abstract String uploadSuffix(InputStream inputStream, String suffix);
}
