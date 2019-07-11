package io.xiongdi.resolver;

import io.xiongdi.annotation.LoginUser;
import io.xiongdi.entity.UserEntity;
import io.xiongdi.interceptor.AuthorizationInterceptor;
import io.xiongdi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 方法参数解析器接口，这个接口是SpringMVC参数解析绑定的核心接口。
 * 不同的参数类型绑定都是通过实现这个接口来实现。
 * 也可以通过实现这个接口来自定义参数解析器
 * @author wujiaxing
 * @date 2019-06-30
 */
@Component
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private UserService userService;

    /**
     * 该解析器是否支持parameter参数的解析
     * @param parameter 拦截到的参数
     * @return 是否符合我们的拦截规则
     * <p>
     *     isAssignableFrom方法作用是判断参数类型是否为UserEntity类或是父类或是父接口
     * </p>
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return  parameter.getParameterType().isAssignableFrom(UserEntity.class) && parameter.hasParameterAnnotation(LoginUser.class);
    }

    /**
     * <p>
     *     将方法参数从给定请求(webRequest)解析为参数值并返回
     *     请求顺序：
     *          1.进入拦截器，拦截有token请求头的用户，说明是验证过的
     *          2.进入自定义参数解析器supportsParameter方法，解析带有LoginUser注解并且类UserEntity或父类的参数
     *          3.进入自定义参数解析器resolveArgument方法，将存在作用域里的userid拿出来查询到user用户信息，赋给UserEntity参数
     * </p>
     * @param parameter
     * @param mavContainer
     * @param webRequest
     * @param binderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 从请求作用域中获取userid
        Object o = webRequest.getAttribute(AuthorizationInterceptor.USER_KEY, RequestAttributes.SCOPE_REQUEST);
        if (o == null) {
            return null;
        }
        UserEntity userEntity = userService.getById((long) o);
        return userEntity;
    }
}
