package io.xiongdi.common.aspect;

import com.google.gson.Gson;
import io.xiongdi.common.annotation.SysLog;
import io.xiongdi.common.utils.DateUtils;
import io.xiongdi.common.utils.HttpContextUtils;
import io.xiongdi.common.utils.IpUtils;
import io.xiongdi.modules.sys.entity.SysLogEntity;
import io.xiongdi.modules.sys.entity.SysUserEntity;
import io.xiongdi.modules.sys.service.SysLogService;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 系统日志，切面处理类
 * @author wujiaxing
 * @date 2019-08-20
 */
@Aspect
@Component
public class SysLogAspect {

    @Autowired
    private SysLogService sysLogService;

    @Pointcut("@annotation(io.xiongdi.common.annotation.SysLog)")
    public void logPoint(){}

    @Around("logPoint()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        // 开始时间
        long startTime = DateUtils.currentMillSeconds();

        // 执行方法
        Object result = point.proceed();

        // 执行时长 = 结束时间 - 开始时间
        long time = DateUtils.currentMillSeconds() - startTime;

        // 保存日志
        saveSysLog(point, time);

        return result;
    }

    /**
     * 保存日志
     * @param point
     * @param time
     */
    private void saveSysLog(ProceedingJoinPoint point, long time) {
        // 方法签名对象
        MethodSignature methodSignature = (MethodSignature) point.getSignature();

        // 方法对象
        Method method = methodSignature.getMethod();

        SysLogEntity sysLogEntity = new SysLogEntity();

        // 设置方法上的注解值
        SysLog sysLog = method.getAnnotation(SysLog.class);
        if (sysLog != null) {
            sysLogEntity.setOperation(sysLog.value());
        }

        // 设置请求方法
        String className = point.getTarget().getClass().getName();
        String methodName = method.getName();
        sysLogEntity.setMethod(className + "." +methodName + "()");

        // 设置请求参数
        Object[] args = point.getArgs();
        String params = new Gson().toJson(args[0]);
        sysLogEntity.setParams(params);

        // 设置请求IP
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        String ip = IpUtils.getIpAddr(request);
        sysLogEntity.setIp(ip);

        // 设置用户名
        String username = ((SysUserEntity) SecurityUtils.getSubject().getPrincipal()).getUsername();
        sysLogEntity.setUsername(username);

        // 设置请求时间
        sysLogEntity.setTime(time);

        // 设置创建时间
        sysLogEntity.setCreateDate(DateUtils.now());

        sysLogService.save(sysLogEntity);
    }
}
