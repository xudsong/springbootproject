package com.spring.xds.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @PostMapping(value = "/hello")
    public String sayHello(@RequestParam(value = "name") String name){
        return "hello "+name+"，this is first messge";
    }
}
