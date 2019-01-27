package com.spring.xds.controller;

import com.spring.xds.feign.ProducerFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired
    private ProducerFeignClient producerFeignClient;

    @PostMapping(value = "/hello/{name}")
    public String helloTest(@PathVariable("name") String name){
        return producerFeignClient.sayHello(name);
    }
}
