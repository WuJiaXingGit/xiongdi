package io.xiongdi.common.validator;

import io.xiongdi.common.exception.XDException;
import org.apache.commons.lang.StringUtils;

/**
 * @author wujiaxing
 * <p>
 *     数据验证类
 * </p>
 */
public abstract class Assert {

    /**
     * 如果字符串为空，则给定异常提示的自定义异常
     * @param o 需要验证的字符串
     * @param msg 异常提示
     */
    public static void isNull(String o, String msg) {
        if (StringUtils.isBlank(o)) {
            throw new XDException(msg);
        }
    }

    /**
     *  如果对象为空，则抛出给定提示的自定义异常
     * @param o 需要验证的对象
     * @param msg 异常提示
     */
    public static void isNull(Object o, String msg) {
        if (o == null) {
            throw new XDException(msg);
        }
    }
}
