package io.xiongdi.annotation;

import java.lang.annotation.*;

/**
 * @author wujiaxing
 * 登录校验
 */
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Login {
}
