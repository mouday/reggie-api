package com.github.mouday.reggie.config;

import com.github.mouday.reggie.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    // 设置静态资源映射
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("设置静态资源映射");

        registry.addResourceHandler("/backend/**")
                .addResourceLocations("classpath:/backend/");

        registry.addResourceHandler("/front/**")
                .addResourceLocations("classpath:/front/");

        // upload
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:upload/");
    }

    /**
     * 扩展消息转换器
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 创建消息转换器对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        // 设置对象转换器
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        // 添加到mvc框架消息转换器中，优先使用自定义转换器
        converters.add(0, messageConverter);
    }
}
