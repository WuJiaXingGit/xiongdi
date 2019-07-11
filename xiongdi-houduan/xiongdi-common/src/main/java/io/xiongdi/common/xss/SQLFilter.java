package io.xiongdi.common.xss;

import io.xiongdi.common.exception.XDException;
import io.xiongdi.common.utils.ResultType;
import org.apache.commons.lang.StringUtils;

/**
 * sql 注入过滤工具
 *
 * @author wujiaxing
 * @date 2019-07-08
 */
public class SQLFilter {

    public static String sqlInject(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        // 替换| ' | " | \ | ; |
        str = StringUtils.replace(str, "'", "");
        str = StringUtils.replace(str, "\"", "");
        str = StringUtils.replace(str, "\\", "");
        str = StringUtils.replace(str, ";", "");

        str = str.toLowerCase();
        // 定义一些要过滤的关键字
        String[] strings = {"master", "truncate", "insert", "select", "delete", "update", "declare", "alter", "drop"};
        for (String s : strings) {
            if (str.indexOf(s) != -1) {
                throw new XDException(ResultType.INCLUTE_ILLEGALITY_PARAM);
            }
        }
        return str;
    }
}
