package com.spring.xds.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 微服务调用方式一：调用方继承该接口即可，不要需要写任何其他代码
 */
public interface TestService {
    @PostMapping("/test")
    public String test(@RequestParam(value = "age") String age);
}
