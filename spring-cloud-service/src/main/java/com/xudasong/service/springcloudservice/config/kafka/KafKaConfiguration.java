package com.xudasong.service.springcloudservice.config.kafka;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description: kafka 属性配置
 */
@ConfigurationProperties(prefix = "spring.kafka")
@Component
@Data
public class KafKaConfiguration {

    /**
     * @Fields bootstrapServer : 集群的地址
     */
    private String bootstrapServers;

    private String producerTopicName;

    private String consumerGroupId;


}
