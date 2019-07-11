package io.xiongdi.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据类
 * @author wujiaxing
 */
public class R extends HashMap<String, Object> {
    /**
     * 无参构造
     */
    public R() {}

    /**
     * 有参构造
     * @param resultType 返回类型枚举
     */
    public R(ResultType resultType) {
        this.put("code", resultType.code());
        this.put("msg", resultType.msg());
    }

    /**
     *  有参构造
     * @param map 返回消息
     */
    public R(Map map) {
        this.putAll(map);
    }

    /**
     * @return 返回一个空的对象
     */
    public static R ok() {
        return new R();
    }

    /**
     * 成功结果包装，追加一个map消息
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V>R ok(Map<K, V> map) {
        return new R(map);
    }

    /**
     * 成功结果包装
     * @param resultType
     * @return
     */
    public static R ok(ResultType resultType) {
        return new R(resultType);
    }

    /**
     * 错误结果包装
     * @return
     */
    public static R error() {
        return new R();
    }

    /**
     * 错误结果包装
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V>R error(Map<K, V> map) {
        return new R(map);
    }

    /**
     * 错误结果包装
     * @param resultType
     * @return
     */
    public static R error(ResultType resultType) {
        return new R(resultType);
    }

    @Override
    public R put(String key, Object value) {
         super.put(key, value);
         return this;
    }
}
