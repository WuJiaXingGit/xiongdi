package io.xiongdi.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * spring 上下文工具
 * @author wujiaxing
 * @date 2019-07-14
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {

    private  static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.applicationContext = applicationContext;
    }

    /**
     *  获取 IOC 容器的 Bean
     * @param name Bean 名字
     * @return
     */
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    /**
     * 获取 IOC 容器的 Bean
     * @param name Bean 名字
     * @param args Bean 的参数
     * @return
     */
    public static Object getBean(String name, Object... args) {
        return applicationContext.getBean(name, args);
    }

    /**
     * 获取 IOC 容器的 Bean
     * @param name Bean 名字
     * @param requiredType Bean 的类型
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name, Class<T> requiredType) {
        return applicationContext.getBean(name, requiredType);
    }

    /**
     * 获取 IOC 容器的 Bean
     * @param requiredType Bean 的类型
     * @param args Bean 的参数
     * @param <T>
     * @return 容器的 Bean
     */
    public static <T> T getBean(Class<T> requiredType, Object... args) {
        return applicationContext.getBean(requiredType, args);
    }

    /**
     *  名字为 name 的 Bean 是否为单例
     * @param name
     * @return
     */
    public static boolean isSingleton(String name) {
        return applicationContext.isSingleton(name);
    }

    /**
     * 名字为 name 的 Bean 在容器中是否存在
     * @param name
     * @return
     */
    public static boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }

    /**
     * 名字为 name 的 Bean 的类型
     * @param name
     * @return
     */
    public static Class<? extends Object> getType(String name) {
        return applicationContext.getType(name);
    }
}
