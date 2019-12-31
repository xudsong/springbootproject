package com.xudasong.service.springcloudservice.service.Impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

@Data
@AllArgsConstructor
@Slf4j
public abstract class Queue implements Runnable{

    private String queueKey;

    private RedisTemplate redisTemplate;

    public Object lPop(){
        ListOperations<String, Object> listOperations = redisTemplate.opsForList();
        //队里中获取数据
        Object object = listOperations.leftPop(queueKey, 0, TimeUnit.MILLISECONDS);
        if (object == null){
            log.warn("队列中数据有误key-》{}", queueKey);
        }
        log.info("队列中数据为-》{}",object);
        return object;
    }

}
