package com.spring.xds.controller;

import com.spring.xds.feign.ProducerFeignClient;
import com.spring.xds.feign.TestServiceFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired
    private ProducerFeignClient producerFeignClient;

    @Autowired
    private TestServiceFeignClient testServiceFeignClient;

    @PostMapping(value = "/hello/{name}")
    public String helloTest(@PathVariable("name") String name){
        return producerFeignClient.sayHello(name);
    }

    @PostMapping(value = "/api/test")
    public String test(){
       String age = testServiceFeignClient.test("29");
       return age;
    }
}
