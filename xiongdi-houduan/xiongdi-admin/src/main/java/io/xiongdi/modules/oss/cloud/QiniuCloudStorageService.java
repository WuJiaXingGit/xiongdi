package io.xiongdi.modules.oss.cloud;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import io.xiongdi.common.exception.XDException;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * 七牛配置策略
 * @author wujiaxing
 * @date 2019-08-04
 */
public class QiniuCloudStorageService extends CloudStorageService{

    private UploadManager uploadManager;
    private String token;

    public QiniuCloudStorageService(CloudStorageConfig config) {
        this.config = config;
        // 初始化
        init();
    }

    /**
     * 初始化配置信息
     */
    private void init() {
        this.uploadManager = new UploadManager(new Configuration(Zone.autoZone()));
        this.token = Auth.create(config.getQiniuAccessKey(), config.getQiniuSecretKey()).uploadToken(config.getQiniuBucketName());
    }

    @Override
    public String upload(byte[] bytes, String path) {
        try {
            Response response = this.uploadManager.put(bytes, path, token);
            if (!response.isOK()) {
                throw new RuntimeException("上传七牛出错:" + response.toString());
            }
        } catch (Exception e) {
            throw new XDException("上传七牛云失败，请核对七牛配置信息", e);
        }
        return config.getQiniuDomain() + "/" + path;
    }

    @Override
    public String uploadSuffix(byte[] bytes, String suffix) {
        return upload(bytes, getPath(config.getQiniuPrefix(), suffix));
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        try {
            byte[] bytes = IOUtils.toByteArray(inputStream);
            return upload(bytes, path);
        } catch (IOException e) {
            throw new XDException("上传文件失败", e);
        }
    }

    @Override
    public String uploadSuffix(InputStream inputStream, String suffix) {
        return upload(inputStream, getPath(config.getQiniuPrefix(), suffix));
    }
}
