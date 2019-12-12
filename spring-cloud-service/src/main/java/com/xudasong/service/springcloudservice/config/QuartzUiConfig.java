package com.xudasong.service.springcloudservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 服务启动时加载静态资源html
 */
@Configuration
@EnableWebMvc
public class QuartzUiConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("JobManager.html")
                .addResourceLocations("classpath:/static/");
    }

}
