package io.xiongdi.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;

/**
 * JSON 转换工具类
 * @author wujiaxing
 * @date 2019-07-14
 */
@Slf4j
public class JsonUtils {

    private final static ObjectMapper om = new ObjectMapper();

    /**
     *  对象转 JSON
     * @param o 对象
     * @return json 字符串
     * @throws JsonProcessingException
     */
    public static String objToString(Object o) throws JsonProcessingException {
        if (o == null) {
            return null;
        }
        return om.writeValueAsString(o);
    }

    /**
     *  对象转 JSON 并格式化输出
     * @param o 对象
     * @return json 字符串
     * @throws JsonProcessingException
     */
    public static String objToStringPretty(Object o) throws JsonProcessingException {
        if (o == null) {
            return null;
        }
        return om.writerWithDefaultPrettyPrinter().writeValueAsString(o);
    }

    /**
     * JSON 字符串转对象
     * @param content json 字符串
     * @param valueType 对象类型
     * @param <T>
     * @return 对象
     * @throws IOException
     */
    public static <T>T objFormString(String content,Class<T> valueType) throws IOException {
        if (StringUtils.isBlank(content) || valueType == null) {
            return null;
        }
        return om.readValue(content, valueType);
    }
}
