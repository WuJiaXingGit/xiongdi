package io.xiongdi.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * redis 操作工具
 * <p>
 *     valueOperations、setOperations 等 和 redisTemplate 既不是父子类关系，也不是接口实现类关系，
 *     之所以能够注入，是因为 spring 在 AbstractBeanFactory 类中的 doGetBean() 方法里有判断不是同一类
 *     型的注入问题，用什么转换呢？
 *     用 spring 的 editor,例如：spring会去加载 ValueOperations+Editor，即ValueOperationsEditor的类。
 *     且此类必须要实现PropertyEditor接口。而我们在ValueOperations的包目录下确实会找到ValueOperationsEditor。
 *     这个类非常简单，它重写了setValue方法，将redisTemplate中的opsForValue()返回值set进去，
 *     而opsForValue()返回值就是继承了ValueOperations的DefaultValueOperations。
 * </p>
 * @author wujiaxing
 * @date 2019-07-14
 */
@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate redisTemplate;
    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOperations;
    @Resource(name = "redisTemplate")
    private SetOperations<String, Object> setOperations;
    @Resource(name = "redisTemplate")
    private ListOperations<String, Object> listOperations;
    @Resource(name = "redisTemplate")
    private HashOperations<String,String, Object> hashOperations;
    @Resource(name = "redisTemplate")
    private ZSetOperations<String, Object> zSetOperations;

    public final static long DEFAULT_EXPIRE = 60 * 60 * 24;
    public final static long NOT_EXPIRE = -1;

    /**
     * 将对象存入 redis
     * @param key 存入的键
     * @param value 存入的值
     * @param expire 过期时间
     * @throws JsonProcessingException json 转换异常
     */
    public void set(String key, Object value, long expire) throws JsonProcessingException {
        // 将值以json形式存入redis
        valueOperations.set(key, JsonUtils.objToString(value));
        // 判断是否要设置过期时间
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    /**
     * 将对象存入 redis
     * @param key 存入的键
     * @param value 存入的值
     * @throws JsonProcessingException json 转换异常
     */
    public void set(String key, Object value) throws JsonProcessingException {
        set(key, value, DEFAULT_EXPIRE);
    }

    /**
     * 查询 redis 中 key 对应的值
     * @param key 要查询的 key
     * @param valueType 查询要返回的对象类型
     * @param expire 过期时间
     * @param <T>
     * @return
     * @throws IOException
     */
    public  <T>T get(String key, Class<T> valueType, long expire) throws IOException {
        // 根据 key 对应 redis 中的 json 值
        String s = valueOperations.get(key);
        // 设置过期时间
        if (expire != DEFAULT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return StringUtils.isBlank(s) ? null : JsonUtils.objFormString(s, valueType);
    }

    /**
     * 查询 redis 中 key 对应的值
     * @param key 要查询的 key
     * @param valueType 查询要返回的对象类型
     * @param <T>
     * @return
     * @throws IOException
     */
    public <T>T get(String key, Class<T> valueType) throws IOException {
        return get(key, valueType, DEFAULT_EXPIRE);
    }

    /**
     * 删除
     * @param key 要删除的 key
     * @return
     */
    public boolean delete(String key) {
        return redisTemplate.delete(key);
    }
}
