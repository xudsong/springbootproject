package com.xudasong.service.springcloudservice.config;

import com.xudasong.service.springcloudservice.constants.RedisKeyConstants;
import com.xudasong.service.springcloudservice.dto.LockResource;
import com.xudasong.service.springcloudservice.service.ITestService;
import com.xudasong.service.springcloudservice.service.Impl.SmsQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class QueueTask {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StartRunner startRunner;

    @Autowired
    private ITestService testService;

    public void initThread(){
        ExecutorService exec = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(8));
        exec.execute(new WaitHandler());
    }

    class WaitHandler implements Runnable{

        @Override
        public void run() {
            ExecutorService exec = new ThreadPoolExecutor(5,5,0L,TimeUnit.MILLISECONDS,new LinkedBlockingQueue<>(8));
            LockResource resource = startRunner.getResource();
            synchronized (resource){
                //                    log.info("队列线程睡眠");
//                    resource.wait();
                log.info("队列线程==》启动线程唤醒");
                exec.execute(new SmsQueue(RedisKeyConstants.QUEUE_SMS,redisTemplate,testService));
                log.info("队列线程==》启动线程完毕");

            }
        }
    }

}
