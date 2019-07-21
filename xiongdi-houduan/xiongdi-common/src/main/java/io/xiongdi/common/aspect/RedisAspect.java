package io.xiongdi.common.aspect;

import io.xiongdi.common.exception.XDException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Redis 切面处理类
 * @author wujiaxing
 * @date 2019-07-21
 */
@Slf4j
@Aspect
@Component
public class RedisAspect {

    @Value("${xd.redis.open: false}")
    private boolean open;

    @Pointcut("execution(* io.xiongdi.common.utils.RedisUtils.*(..))")
    public void pointcut(){}

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) {
        Object result = null;
        if (open) {
            try {
                result = point.proceed();
            } catch (Throwable throwable) {
                log.error("Redis服务异常", throwable);
                throw new XDException("Redis服务异常", throwable);
            }
        }
        return result;
    }
}
