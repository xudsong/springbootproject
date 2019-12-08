package com.spring.xds.feign;

import com.spring.xds.api.TestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 方式一调用方法
 */
@FeignClient(value = "spring-cloud-producer")
public interface TestServiceFeignClient extends TestService{
}
