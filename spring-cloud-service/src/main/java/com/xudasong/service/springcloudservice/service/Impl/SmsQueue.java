package com.xudasong.service.springcloudservice.service.Impl;

import com.xudasong.service.springcloudservice.service.ITestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
public class SmsQueue extends Queue{

    private ITestService testService;

    public SmsQueue(String queueKey, RedisTemplate redisTemplate, ITestService testService) {
        super(queueKey, redisTemplate);
        this.testService = testService;
    }

    @Override
    public void run() {
        while (true){
            try {
                Object object = this.lPop();
                testService.sendSms(object);
            } catch (Exception e) {
                log.warn("处理队列异常---》{}", e);
            }
        }
    }
}
