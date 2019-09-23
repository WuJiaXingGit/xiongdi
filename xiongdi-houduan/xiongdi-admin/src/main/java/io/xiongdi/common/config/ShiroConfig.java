package io.xiongdi.common.config;

import io.xiongdi.modules.sys.shiro.Auth2Filter;
import io.xiongdi.modules.sys.shiro.UserRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.ServletContainerSessionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro 的配置
 * @author wujiaxing
 * @date 2019-07-27
 */
@Configuration
public class ShiroConfig {

    /**
     * 单机环境， session 交给 shiro 管理
     * <p>
     *     havingValue属性：通过其两个属性name以及havingValue来实现的，其中name用来从application.properties中读取某个属性值，
     *     如果该值为空，则返回false;如果值不为空，则将该值与havingValue指定的值进行比较，如果一样则返回true;否则返回false。
     *     如果返回值为false，则该configuration不生效；为true则生效
     * </p>
     */
    @Bean
    @ConditionalOnProperty(prefix = "xd", name = "cluster", havingValue = "false")
    public DefaultWebSessionManager sessionManager(@Value("${xd.globalSessionTimeout: 3600}") long globalSessionTimeout) {
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        defaultWebSessionManager.setSessionIdUrlRewritingEnabled(false);
        defaultWebSessionManager.setGlobalSessionTimeout(globalSessionTimeout * 1000);
        defaultWebSessionManager.setSessionValidationInterval(globalSessionTimeout *  1000);
        defaultWebSessionManager.setSessionValidationSchedulerEnabled(true);
        defaultWebSessionManager.setSessionIdCookieEnabled(true);
        return defaultWebSessionManager;
    }

    /**
     * 集群环境，session 交给 spring-session 管理
     * <p>
     *     ServletContainerSessionManager 和 DefaultWebSessionManager 都是 WebSessionManager 接口的实现类
     * </p>
     */
    @Bean
    @ConditionalOnProperty(prefix = "xd", name = "cluster", havingValue = "true")
    public ServletContainerSessionManager servletContainerSessionManager() {
        return new ServletContainerSessionManager();
    }

    /**
     * shiro 的安全管理器
     * @return
     */
    @Bean("securityManager")
    public SecurityManager securityManager(UserRealm userRealm, SessionManager sessionManager) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        // 将自定义Realm交给shiro的安全管理器
        defaultWebSecurityManager.setRealm(userRealm);
        // 将 session 管理器交给shiro的安全管理器
        defaultWebSecurityManager.setSessionManager(sessionManager);
        // 记得我
        defaultWebSecurityManager.setRememberMeManager(null);
        return defaultWebSecurityManager;
    }

    /**
     * 设置 shiro 的各个过滤器的职责
     * @param securityManager
     * @return
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 设置登录页面
        shiroFilterFactoryBean.setLoginUrl("/login.html");
        // 设置权限不足时跳转的页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/register.html");
        //设置自定义的过滤器
        Map<String, Filter> filters = new HashMap<String, Filter>();
        filters.put("auth2", new Auth2Filter());
        shiroFilterFactoryBean.setFilters(filters);
        // 设置一些特定的页面交给 shiro 的指定过滤器处理
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/swagger/**", "anon");
        filterMap.put("/v2/api-docs", "anon");
        filterMap.put("/swagger-ui.html", "anon");
        filterMap.put("/webjars/**", "anon");
        filterMap.put("/swagger-resources/**", "anon");

        filterMap.put("/statics/**", "anon");
        filterMap.put("/login.html", "anon");
        filterMap.put("/sys/login", "anon");
        filterMap.put("/favicon.ico", "anon");
        filterMap.put("/captcha.jpg", "anon");
        filterMap.put("/**", "auth2");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 统一管理 shiro 的一些 bean 的生命周期，比如：初始化 init()、销毁 destroy()
     * @return
     */
    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 用于开启 controller 层的注解，必须先配置 LifecycleBeanPostProcessor
     * @param securityManager
     * @return
     */
    @Bean("authorizationAttributeSourceAdvisor")
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
