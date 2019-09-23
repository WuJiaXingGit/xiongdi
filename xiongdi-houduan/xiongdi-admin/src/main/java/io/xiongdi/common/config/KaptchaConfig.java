package io.xiongdi.common.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 验证码类配置
 * @author wujiaxing
 * @date 2019-07-27
 */
@Configuration
public class KaptchaConfig {

    @Bean
    public DefaultKaptcha defaultKaptcha() {
        // 返回对象
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        // 属性对象
        Properties properties = new Properties();
        properties.put("kaptcha.border", "no");
        properties.put("kaptcha.textproducer.font.color", "black");
        properties.put("kaptcha.textproducer.char.space", "5");
        properties.put("kaptcha.textproducer.font.names", "Arial,Courier,cmr10,宋体,楷体,微软雅黑");
        // 配置对象
        Config config =new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
