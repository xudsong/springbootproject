package com.xudasong.service.springcloudservice.service.Impl;

import com.xudasong.service.springcloudservice.config.exception.BizException;
import com.xudasong.service.springcloudservice.service.ITestService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.xudasong.service.springcloudservice.enums.ErrorCode.LESS_PARAM_ERROR;

@Service
@Slf4j
public class TestServiceImpl implements ITestService{

    @Override
    @Async
    public String test(String name){
        if(StringUtils.isEmpty(name)||name.equals("“”")){
            log.warn("name为空！");
            throw new BizException(LESS_PARAM_ERROR.getCode(),LESS_PARAM_ERROR.getMessage());
        }
        try {
            Thread.sleep(0L);
        }catch (Exception e){
            throw new BizException(LESS_PARAM_ERROR.getCode(),LESS_PARAM_ERROR.getMessage());
        }
        for (int i=0;i<5;i++) {
            System.out.println(name);
        }
        return name;
    }

    public void sendSms(Object object) throws InterruptedException {
        log.info("消息发送，手机号为{}",(String)object);
        Thread.sleep(10000);
    }

}
