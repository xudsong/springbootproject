package com.xudasong.service.springcloudservice.service.Impl;

import com.xudasong.service.springcloudservice.constants.RedisKeyConstants;
import com.xudasong.service.springcloudservice.service.ISendQueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SendQueueServiceImp implements ISendQueueService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void sendQueue(String mobile) {
        this.sendToQueue(mobile);
    }

    public void sendToQueue(Object object){
        ListOperations<String, Object> listOperations = redisTemplate.opsForList();
        listOperations.rightPush(RedisKeyConstants.QUEUE_SMS, object);
    }
}
