package io.xiongdi.annotation;

import java.lang.annotation.*;

/**
 * @author wujiaxing
 * @date 2019-07-12
 * 登录校验
 */
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Login {
}
