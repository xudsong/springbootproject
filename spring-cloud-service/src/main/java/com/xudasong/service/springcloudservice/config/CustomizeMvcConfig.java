package com.xudasong.service.springcloudservice.config;

import com.xudasong.service.springcloudservice.config.filter.LogFilter;
import com.xudasong.service.springcloudservice.config.filter.ParameterFilter;
import com.xudasong.service.springcloudservice.config.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

/**
 * 追加拦截器
 */
@Configuration
public class CustomizeMvcConfig implements WebMvcConfigurer {
    @Value("@profilesActive@")
    private String profile;

    @Bean
    public FilterRegistrationBean<LogFilter> logFilter(){
        FilterRegistrationBean<LogFilter> frBean = new FilterRegistrationBean<>();
        frBean.setFilter(new LogFilter());
        frBean.addUrlPatterns("/*");
        frBean.setOrder(1);
        return frBean;
    }

    @Bean
    public Filter paramterFilter(){return new ParameterFilter();}

    @Bean
    public TokenInterceptor tokenInterceptor(){return new TokenInterceptor();}

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        if (null != profile && profile.equals("dev")){
            SwaggerFilterTool.addResourceHandlers(registry);
        }
    }

    /**
     * 新增内部拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        InterceptorRegistration interceptor = registry.addInterceptor(tokenInterceptor())
                //修改拦截为网关进来的才拦截获取userinfo
                .addPathPatterns("/api/**")
                // 错误地址直接放行，不获取userinfo
                .excludePathPatterns("/error")
                //内部直接调用放行，不获取userinfo
                .excludePathPatterns("/feign/**")
                //网关放行接口直接放行，不获取userinfo
                .excludePathPatterns("/api/release/**");

        if (null != profile && profile.equals("dev")){
            SwaggerFilterTool.interceptorRelease(interceptor);
            //让测试远程调用的方法通过
            interceptor.excludePathPatterns("/test/**");
        }
    }
}
