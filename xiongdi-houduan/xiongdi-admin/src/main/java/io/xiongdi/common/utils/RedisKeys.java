package io.xiongdi.common.utils;

/**
 * redis 所有的 key
 * @author wujiaxing
 * @date 2019-07-24
 */
public class RedisKeys {

    /**
     * 获取系统配置 key
     * @param key
     * @return
     */
    public static String getSysConfigKey(String key) {
        return "sys:config:" + key;
    }

    /**
     * 获取shiro框架中的 session key
     * @param key
     * @return
     */
    public static String getShiroSessionKey(String key) {
        return "sessionid:" + key;
    }
}
