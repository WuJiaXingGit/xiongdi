package io.xiongdi.modules.oss.cloud;

import com.aliyun.oss.OSSClient;
import io.xiongdi.common.exception.XDException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 阿里云配置策略
 * @author wujiaxing
 * @date 2019-08-05
 */
public class AliyunCloudStorageService extends CloudStorageService {

    private OSSClient client;

    public AliyunCloudStorageService(CloudStorageConfig config) {
        this.config = config;
        // 初始化
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        client = new OSSClient(config.getAliyunEndPoint(), config.getAliyunAccessKeyId(), config.getAliyunAccessKeySecret());
    }

    @Override
    public String upload(byte[] bytes, String path) {
        return upload(new ByteArrayInputStream(bytes), path);
    }

    @Override
    public String uploadSuffix(byte[] bytes, String suffix) {
        return upload(bytes, getPath(config.getAliyunPrefix(), suffix));
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        try {
            client.putObject(config.getAliyunBucketName(), path, inputStream);
        }catch (Exception e) {
            throw new XDException("上传文件失败", e);
        }
        return config.getAliyunDomain() + "/" + path;
    }

    @Override
    public String uploadSuffix(InputStream inputStream, String suffix) {
        return upload(inputStream, getPath(config.getAliyunPrefix(), suffix));
    }
}
