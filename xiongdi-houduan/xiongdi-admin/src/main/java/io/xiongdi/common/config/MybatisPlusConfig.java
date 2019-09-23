package io.xiongdi.common.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis-plus 配置
 * @author wujiaxing
 * @date 2019-07-27
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 分页插件
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
    /**
     * <p>
     *     sql注入器，有两种方式
     *          1、logicSqlInjector,SQL逻辑删除注入器（就是现在这种）
     *          2、DefaultSqlInjector,SQL默认注入器，如果用默认的，就不要配置这个bean
     * </p>
     * @return sql注入器
     */
    public ISqlInjector iSqlInjector(){
        return new LogicSqlInjector();
    }
}
