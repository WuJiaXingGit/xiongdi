package io.xiongdi.common.annotation;

import java.lang.annotation.*;

/**
 * 系统日志注解
 * @author wujiaxing
 * @date 2019-07-24
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {
    String value() default "";
}
