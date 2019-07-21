package io.xiongdi.commons.dynamic.datasource.aspect;

import io.xiongdi.commons.dynamic.datasource.annotation.DataSource;
import io.xiongdi.commons.dynamic.datasource.config.DynamicContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 父类有注解，但子类没有注解的话，@within和@target是不会对子类生效的。
 * 子类没有注解的情况下，只有没有被重写的有注解的父类的方法才能被@within匹配到。
 * 如果父类无注解，子类有注解的话，@target对父类所有方法生效，@within只对重载过的方法生效。
 * @author wujiaxing
 * @date 2019-07-21
 */
@Slf4j
@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DataSourceAspect {

    @Pointcut("@annotation(io.xiongdi.commons.dynamic.datasource.annotation.DataSource) " +
            "|| @within(io.xiongdi.commons.dynamic.datasource.annotation.DataSource)")
    public void pointcut(){}

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        // 获取方法名
        MethodSignature signature = (MethodSignature) point.getSignature();

        // 根据方法名获取方法
        Method method = signature.getMethod();
        // 获取目标类
        Class<?> targerClass = point.getTarget().getClass();

        // 获取类或方法上的注解
        DataSource methodAnnotation = method.getAnnotation(DataSource.class);
        DataSource classAnnotation = targerClass.getAnnotation(DataSource.class);

        if (methodAnnotation != null || classAnnotation != null) {
            String value;
            if (methodAnnotation != null) {
                value = methodAnnotation.value();
            } else {
                value = classAnnotation.value();
            }
            DynamicContextHolder.push(value);
        }

        try {
           return  point.proceed();
        } finally {
            DynamicContextHolder.poll();
            log.debug("清空数据源");
        }
    }
}
