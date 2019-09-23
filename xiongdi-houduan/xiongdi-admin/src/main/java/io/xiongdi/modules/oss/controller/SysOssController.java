package io.xiongdi.modules.oss.controller;

import com.google.gson.Gson;
import io.xiongdi.common.exception.XDException;
import io.xiongdi.common.utils.*;
import io.xiongdi.common.validator.ValidatorUtils;
import io.xiongdi.common.validator.group.AliyunGroup;
import io.xiongdi.common.validator.group.QcloudGroup;
import io.xiongdi.common.validator.group.QiniuGroup;
import io.xiongdi.modules.oss.cloud.CloudStorageConfig;
import io.xiongdi.modules.oss.cloud.OSSFactory;
import io.xiongdi.modules.oss.entity.SysOssEntity;
import io.xiongdi.modules.oss.service.SysOssService;
import io.xiongdi.modules.sys.service.SysConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * 文件上传 controller
 * @author wujiaxing
 * @date 2019-08-06
 */
@RestController
@RequestMapping("/sys/oss")
public class SysOssController {

    @Autowired
    private SysOssService sysOssService;
    @Autowired
    private SysConfigService sysConfigService;

    private final static String KEY = ConfigConstant.CLOUD_STORAGE_CONFIG_KEY;

    /**
     * 列表
     * @param params
     * @return
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:oss:all")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysOssService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 云存储配置
     * @return
     */
    @RequestMapping("/config")
    @RequiresPermissions("sys:oss:all")
    public R config() throws IOException {
        CloudStorageConfig config = sysConfigService.getConfigObject(KEY, CloudStorageConfig.class);

        return R.ok().put("config",config);
    }

    /**
     * 修改云存储配置信息
     * @param config
     * @return
     */
    @RequestMapping("/saveConfig")
    @RequiresPermissions("sys:oss:all")
    public R saveConfig(@RequestBody CloudStorageConfig config) {
        // 校验数据
        ValidatorUtils.validateEntity(config);

        if (config.getType() == Constant.CloudService.QINIU.getValue()) {
            // 七牛云校验
            ValidatorUtils.validateEntity(config, QiniuGroup.class);
        } else if (config.getType() == Constant.CloudService.ALIYUN.getValue()) {
            // 阿里云校验
            ValidatorUtils.validateEntity(config, AliyunGroup.class);
        } else if (config.getType() == Constant.CloudService.QCLOUD.getValue()) {
            // 腾讯云校验
            ValidatorUtils.validateEntity(config, QcloudGroup.class);
        }

        sysConfigService.updateValueByKey(KEY, new Gson().toJson(config));

        return R.ok();
    }

    /**
     * 文件上传
     * @param file
     * @return
     */
    @RequestMapping("/upload")
    @RequiresPermissions("sys:oss:all")
    public R upload(@RequestParam("file")MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new XDException("上传文件不能为空");
        }

        // 上传文件
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);

        // 保存文件信息
        SysOssEntity ossEntity = new SysOssEntity();
        ossEntity.setCreateDate(DateUtils.now());
        ossEntity.setUrl(url);
        sysOssService.save(ossEntity);

        return R.ok().put("url", url);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:oss:all")
    public R delete(@RequestBody Long[] ids) {
        sysOssService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
}
