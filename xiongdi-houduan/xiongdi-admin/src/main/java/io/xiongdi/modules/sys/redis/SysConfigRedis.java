package io.xiongdi.modules.sys.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.xiongdi.common.utils.RedisKeys;
import io.xiongdi.common.utils.RedisUtils;
import io.xiongdi.modules.sys.entity.SysConfigEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 系统配置 redis
 * @author wujiaxing
 * @date 2019-08-08
 */
@Component
public class SysConfigRedis {

    @Autowired
    private RedisUtils redisUtils;


    /**
     * 保存或修改
     */
    public void saveOrUpdate(SysConfigEntity config) throws JsonProcessingException {
        if (config == null) {
            return;
        }
        // 创建要放入 Redis 的Key
        String key = RedisKeys.getSysConfigKey(config.getParamKey());
        redisUtils.set(key, config);
    }

    /**
     * 删除
     */
    public void delete(String configKey) {
        String key = RedisKeys.getSysConfigKey(configKey);
        redisUtils.delete(key);
    }

    /**
     * 查询
     */
    public SysConfigEntity get(String configKey) throws IOException {
        String key = RedisKeys.getSysConfigKey(configKey);
        return redisUtils.get(key, SysConfigEntity.class);
    }

}
