package com.xudasong.service.springcloudservice.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RedisReceiver {

    public void receiveMessage(String message) {
        log.info("消息来了："+message);
        //这里是收到通道的消息之后执行的方法
    }

}
