package io.xiongdi.commons.dynamic.datasource.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.xiongdi.commons.dynamic.datasource.properties.DataSourceProperties;
import io.xiongdi.commons.dynamic.datasource.properties.DynamicDataSourceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 多数据源配置
 * @author wujiaxing
 * @date 2019-07-21
 */
@Configuration
@EnableConfigurationProperties(DynamicDataSourceProperties.class)
public class DynamicDataSourceConfig {

    @Autowired
    private DynamicDataSourceProperties properties;

    /**
     * 这个主要是要 spring 注入参数用的，当默认数据源
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DynamicDataSource dynamicDataSource(DataSourceProperties properties) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 配置多个数据源
        dynamicDataSource.setTargetDataSources(getDynamicDataSource());

        // 设置默认数据源
        DruidDataSource defaultDataSource = DynamicDataSourceFactory.buildDruidDataSource(properties);
        dynamicDataSource.setDefaultTargetDataSource(defaultDataSource);

        return dynamicDataSource;
    }

    /**
     * 根据 在 yml 文件配置成的 DynamicDataSourceProperties 类，通过自定义的数据源工厂转成 spring 可以认识的多个数据源
     * @return
     */
    private Map<Object, Object> getDynamicDataSource() {
        Map<String, DataSourceProperties> dataSourcePropertiesMap = properties.getDatasource();
        Map<Object, Object> targerDataSource = new LinkedHashMap<>(dataSourcePropertiesMap.size());

        dataSourcePropertiesMap.forEach((k,v) -> {
            DruidDataSource druidDataSource = DynamicDataSourceFactory.buildDruidDataSource(v);
            targerDataSource.put(k,druidDataSource);
        });

        return targerDataSource;
    }

}
