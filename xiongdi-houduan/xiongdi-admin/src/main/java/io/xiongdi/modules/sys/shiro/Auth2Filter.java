package io.xiongdi.modules.sys.shiro;

import com.google.gson.Gson;
import io.xiongdi.common.utils.HttpContextUtils;
import io.xiongdi.common.utils.R;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 扩展shiro的auth过滤器
 * @author wujiaxing
 * @date 2019-09-01
 */
public class Auth2Filter extends AuthenticatingFilter {

    /**
     * 在执行executeLogin方法异常，登陆失败后处理
     * @param token
     * @param e
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        // 设置响应头
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());

        Throwable throwable = e.getCause() == null ? e : e.getCause();
        R r = R.error(HttpStatus.SC_UNAUTHORIZED, throwable.getMessage());
        String josn = new Gson().toJson(r);
        try {
            httpResponse.getWriter().print(josn);
        } catch (IOException e1) {
        }
         return false;
    }

    /**
     * 是否允许被访问
     * <p>
     *     当跨域时，浏览器会发送一个Request Method类型为options的预请求，
     *     用于试探服务端是否能接受真正的请求，如果options获得的回应是拒绝性质的，
     *     比如404\403\500等http状态，就会停止post、put等请求的发出
     * </p>
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (((HttpServletRequest)request).getMethod().equals(RequestMethod.OPTIONS.name())) {
            return true;
        }
        return super.isAccessAllowed(request,response,mappedValue);
    }

    /**
     *流程未经身份验证的请求,会调用executeLogin方法，然后
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {

        String token = getRequestToken((HttpServletRequest) request);
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return new Auth2Token(token);
    }

    /**
     * 流程未经身份验证的请求
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token，如果token不存在，直接返回401
        String token = getRequestToken((HttpServletRequest) request);
        if(StringUtils.isBlank(token)){
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());

            String json = new Gson().toJson(R.error(HttpStatus.SC_UNAUTHORIZED, "invalid token"));
            httpResponse.getWriter().print(json);

            return false;
        }
        return executeLogin(request, response);
    }

    /**
     * 在请求中获取token
     * @param request
     * @return
     */
    private String getRequestToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            token = request.getParameter("token");
        }
        return token;
    }
}
