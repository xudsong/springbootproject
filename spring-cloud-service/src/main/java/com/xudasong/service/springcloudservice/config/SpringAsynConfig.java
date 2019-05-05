package com.xudasong.service.springcloudservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 异步调用线程池配置
 * 在需要异步调用的方法上加入注解@Async即可实现异步调用
 */
@Configuration
@EnableAsync
public class SpringAsynConfig {

    @Bean
    public AsyncTaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(10);
        return executor;
    }

}
