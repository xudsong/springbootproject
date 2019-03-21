package com.xudasong.service.springcloudservice.config;

import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

public final class SwaggerFilterTool {

    public static void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler(new String[]{"/"}).addResourceLocations(new String[]{"classpath:/static/"});
        registry.addResourceHandler(new String[]{"/csrf"}).addResourceLocations(new String[]{"classpath:/static/"});
        registry.addResourceHandler(new String[]{"swagger-ui.html"}).addResourceLocations(new String[]{"classpath:/META-INF/resources/"});
        registry.addResourceHandler(new String[]{"/webjars/**"}).addResourceLocations(new String[]{"classpath:/META-INF/resources/webjars/"});
    }

    public static void interceptorRelease(InterceptorRegistration interceptor){
        interceptor.excludePathPatterns(new String[]{"/webjars/springfox-swagger-ui/**"}).excludePathPatterns(new String[]{"/swagger-ui.html*"})
        .excludePathPatterns(new String[]{"/swagger-resources/**"}).excludePathPatterns(new String[]{"/v2/api-docs/**"})
        .excludePathPatterns(new String[]{"/"}).excludePathPatterns(new String[]{"/csrf"}).excludePathPatterns(new String[]{"/validatorUrl**"});
    }

    private SwaggerFilterTool(){}

}
