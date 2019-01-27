package com.spring.xds.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "spring-cloud-producer")
public interface ProducerFeignClient {
    @PostMapping(value = "/hello")
    public String sayHello(@RequestParam(value = "name") String name);
}
