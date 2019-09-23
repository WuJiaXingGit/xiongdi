package io.xiongdi.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.xiongdi.common.utils.PageUtils;
import io.xiongdi.modules.sys.entity.SysConfigEntity;

import java.io.IOException;
import java.util.Map;

/**
 * 系统信息配置
 * @author wujiaxing
 * @date 2019-08-10
 */
public interface SysConfigService extends IService<SysConfigEntity> {

    /**
     * 查询分页
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 保存配置
     * @param config
     */
    void saveConfig(SysConfigEntity config) throws JsonProcessingException;

    /**
     * 更新配置
     * @param config
     */
    void update(SysConfigEntity config) throws JsonProcessingException;

    /**
     * 根据 key，更新 value
     * @param key
     * @param value
     */
    void updateValueByKey(String key, String value);

    /**
     * 批量删除配置信息
     * @param ids
     */
    void deleteBatch(Long[] ids);

    /**
     * 根据 key ,获取配置的value值
     * @param key key
     * @return
     */
    String getValue(String key) throws IOException;

    /**
     * 根据key，获取value的object对象
     * @param key  key
     * @param clazz object 对象
     * @return
     */
    <T>T getConfigObject(String key, Class<T> clazz) throws IOException;
}
