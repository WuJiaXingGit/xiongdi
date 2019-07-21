package io.xiongdi.commons.dynamic.datasource.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 多数据源配置的属性
 * @author wujiaxing
 * @date 2019-07-21
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "dynamic")
public class DynamicDataSourceProperties {

    private Map<String, DataSourceProperties> datasource = new LinkedHashMap<>();
}
