package com.xudasong.service.springcloudservice.controller;

import com.xudasong.service.springcloudservice.common.CommonResponse;
import com.xudasong.service.springcloudservice.service.ISendQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisQueueController {

    @Autowired
    private ISendQueueService sendQueueService;

    @PostMapping("/api/release/sendQueue")
    public CommonResponse sendQueue(String mobile){
        sendQueueService.sendQueue(mobile);
        return CommonResponse.success();
    }

}
