package com.xudasong.service.springcloudservice.controller;

import com.xudasong.service.springcloudservice.common.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class KafkaController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/api/release/kafka/testSendMsg")
    public CommonResponse testSendMsg(@RequestParam("name")String name){

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("topinfo","name", name);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("生产者-发送消息成功：" + result.toString());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.info("生产者-发送消息失败：" + ex.getMessage());
            }
        });
        return new CommonResponse();
    }

    /**
     * @Description: 可以同时订阅多主题，只需按数组格式即可，也就是用“，”隔开
     * 如果多个集群的情况下，需要在KafkaListener监听注解上添加containerFactory，对应配置中的监听容器工厂,如：
     * 换成@KafkaListener(topics = { "topinfo" }, containerFactory = "kafkaListenerContainerFactory")
     * @param record
     * @return
     */
    @KafkaListener(topics = { "topinfo" })
    public CommonResponse testReceiveMsg(ConsumerRecord<?, ?> record){

        log.info("消费得到的消息---key: " + record.key());
        log.info("消费得到的消息---value: " + record.value().toString());
        return new CommonResponse();
    }

}
