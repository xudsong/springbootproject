package com.xudasong.service.springcloudservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

//@EnableCaching
//@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@SpringBootApplication
public class SpringCloudServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudServiceApplication.class, args);
	}

}
