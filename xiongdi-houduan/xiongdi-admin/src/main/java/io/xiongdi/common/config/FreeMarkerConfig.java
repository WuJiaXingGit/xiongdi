package io.xiongdi.common.config;

import io.xiongdi.modules.sys.shiro.ShiroTag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * FreeMarker模板配置
 * @author wujiaxing
 * @date 2019-07-27
 */
@Configuration
public class FreeMarkerConfig {

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer(ShiroTag shiroTag) {
        // 模板路径
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        Map<String,Object> variables = new HashMap<>(1);
        variables.put("shiro", shiroTag);
        freeMarkerConfigurer.setFreemarkerVariables(variables);

        Properties properties = new Properties();
        properties.setProperty("default_encoding", "utf-8");
        properties.setProperty("number_format", "0.##");
        freeMarkerConfigurer.setFreemarkerSettings(properties);
        freeMarkerConfigurer.setTemplateLoaderPath("classpath:/templates");

        return freeMarkerConfigurer;
    }
}
