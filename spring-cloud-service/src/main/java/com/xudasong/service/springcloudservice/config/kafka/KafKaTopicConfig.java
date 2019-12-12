package com.xudasong.service.springcloudservice.config.kafka;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: kafka 主题 配置类
 */
@Configuration
public class KafKaTopicConfig {

    @Autowired
    private KafKaConfiguration configuration;

    /**
     * @Description: kafka管理员，委派给AdminClient以创建在应用程序上下文中定义的主题的管理员。
     */
    @Bean
    public KafkaAdmin kafkaAdmin() {

        Map<String, Object> props = new HashMap<>();

        // 配置Kafka实例的连接地址
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, configuration.getBootstrapServers());
        KafkaAdmin admin = new KafkaAdmin(props);
        return admin;
    }

    /**
     * @Description: kafka的管理客户端，用于创建、修改、删除主题等
     */
    @Bean
    public AdminClient adminClient() {
        return AdminClient.create(kafkaAdmin().getConfig());
    }

    /**
     * @Description: 创建一个新的 topinfo 的Topic，如果kafka中topinfo 的topic已经存在，则忽略。
     */
    @Bean
    public NewTopic topinfo() {

        // 主题名称
        String topicName = configuration.getProducerTopicName();
        // 第二个参数是分区数， 第三个参数是副本数量，确保集群中配置的数目大于等于副本数量
        return new NewTopic(topicName, 2, (short) 2);
    }

}
