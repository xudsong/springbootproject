package com.xudasong.service.springcloudservice.config;

import com.xudasong.service.springcloudservice.dto.LockResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StartRunner implements CommandLineRunner {

    private LockResource resource;

    @Autowired
    private QueueTask queueTask;

    @Override
    public void run(String... args) throws Exception {

        resource = new LockResource();
        if (true){
            log.info("init queue task thread");
            queueTask.initThread();
        }

    }

    public LockResource getResource(){
        return resource;
    }
}
