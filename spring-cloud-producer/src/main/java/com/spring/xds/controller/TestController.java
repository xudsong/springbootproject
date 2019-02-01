package com.spring.xds.controller;

import com.spring.xds.api.TestService;
import com.spring.xds.service.TestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 方式一调用时，controller层需实现api接口
 */
@RestController
public class TestController implements TestService {
    @Autowired
    private TestServiceImpl testService;

    @Override
    public String test(String age) {
        return testService.test(age);
    }
}
