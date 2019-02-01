package com.spring.xds.service;

import com.spring.xds.api.TestService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class TestServiceImpl {

    public String test(String age) {
        return age;
    }
}
