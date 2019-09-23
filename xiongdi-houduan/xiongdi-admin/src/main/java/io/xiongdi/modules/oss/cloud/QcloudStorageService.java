package io.xiongdi.modules.oss.cloud;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.request.UploadFileRequest;
import com.qcloud.cos.sign.Credentials;
import io.xiongdi.common.exception.XDException;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * 腾讯云配置策略
 * @author wujiaxing
 * @date 2019-08-05
 */
public class QcloudStorageService extends CloudStorageService {

    private COSClient client;

    public QcloudStorageService(CloudStorageConfig config) {
        this.config = config;
        // 初始化
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        Credentials credentials = new Credentials(config.getQcloudAppId(), config.getQcloudSecretId(), config.getQcloudSecretKey());
        // 初始化客户端配置
        ClientConfig clientConfig = new ClientConfig();
        // 设置 bucket 所在的区域，华南：gz  华北：tj  华东：sh
        clientConfig.setRegion(config.getQcloudRegion());
        client = new COSClient(clientConfig, credentials);
    }
    @Override
    public String upload(byte[] bytes, String path) {
        // 腾讯云必须要以 / 开头
        String preKey = "/";
        if (!path.startsWith(preKey)) {
            path = "/" + path;
        }

        // 上传到腾讯云
        UploadFileRequest fileRequest = new UploadFileRequest(config.getQcloudBucketName(), path, bytes);
        String response = client.uploadFile(fileRequest);

        JSONObject object = JSONObject.fromObject(response);
        if (object.getInt("code") != 0) {
            throw new XDException("上传文件失败," + object.getString("message"));
        }
        return config.getQcloudDomain() + "/" + path;
    }

    @Override
    public String uploadSuffix(byte[] bytes, String suffix) {
        return upload(bytes, getPath(config.getQcloudPrefix(), suffix));
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
        return upload(inputStream, getPath(config.getQcloudPrefix(), suffix));
    }
}
