package com.spring.xds.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微服务调用方式二：调用方定义接口，并在接口类定义与提供方一样的方法名和参数列表
 */
@RestController
public class HelloController {
    @PostMapping(value = "/hello")
    public String sayHello(@RequestParam(value = "name") String name){
        return "hello "+name+"，this is first messge";
    }
}
