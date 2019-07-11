package io.xiongdi.interceptor;

import io.xiongdi.annotation.Login;
import io.xiongdi.common.exception.XDException;
import io.xiongdi.common.utils.ResultType;
import io.xiongdi.entity.TokenEntity;
import io.xiongdi.service.TokenService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

/**
 * 权限（token）验证
 * @author wujiaxing
 * @date 2019-06-30
 */
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

    public final static String USER_KEY = "userId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Login annotation;
        // 如果处理对象是一个处理方法，则获取到方法上的注解
        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod)handler).getMethodAnnotation(Login.class);
        // 否则直接放过拦截的请求
        } else {
            return true;
        }
        // 说明此方法没有Login注解
        if (annotation == null) {
            return true;
        }

        // 从请求头获取token
        String token = request.getHeader("token");

        // 如果请求头没有token,则从请求参数中取
        if (StringUtils.isBlank(token)) {
            token = request.getParameter("token");
        }
        // 如果还是没有token,则抛异常
        if (StringUtils.isBlank(token)) {
           throw new XDException(ResultType.TOKEN_NULL);
        }

        // 查询token信息
        TokenEntity tokenEntity = tokenService.queryByToken(token);

        // 如果token信息是否为null或是否过期，则抛异常
        if (tokenEntity == null || tokenEntity.getExpireTime().isBefore(LocalDateTime.now())) {
            throw new XDException(ResultType.TOKEN_EXPIRE);
        }

        // 否则，存入request作用域,后续根据userId，获取用户信息
        request.setAttribute(USER_KEY, tokenEntity.getUserId());

        return true;
    }
}
