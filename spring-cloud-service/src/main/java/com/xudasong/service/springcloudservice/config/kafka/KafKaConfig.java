package com.xudasong.service.springcloudservice.config.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: kafka配置类
 */
@Configuration
public class KafKaConfig {

    @Autowired
    private KafKaConfiguration configuration;

    /**
     * @Description: 生产者的配置
     */
    public Map<String, Object> producerConfigs() {

        Map<String, Object> props = new HashMap<String, Object>();
        // 集群的服务器地址
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, configuration.getBootstrapServers());
        //  消息缓存
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 40960);
        // 生产者空间不足时，send()被阻塞的时间，默认60s
        props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 6000);
        // 生产者重试次数
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        // 指定ProducerBatch（消息累加器中BufferPool中的）可复用大小
        props.put(ProducerConfig.BATCH_SIZE_CONFIG,  4096);
        // 生产者会在ProducerBatch被填满或者等待超过LINGER_MS_CONFIG时发送
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        // key 和 value 的序列化 "org.apache.kafka.common.serialization.StringSerializer"
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // 客户端id
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "producer.client.id.topinfo");
        //判别请求是否为完整的条件（就是是判断是不是成功发送了）。我们指定了“all”将会阻塞消息，这种设置性能最低，但是是最可靠的。
        props.put(ProducerConfig.ACKS_CONFIG,"all");

        return props;
    }

    /**
     * @Description: 生产者工厂
     */
    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<String, String>(producerConfigs());
    }

    /**
     * @Description: KafkaTemplate
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<String, String>(producerFactory());
    }

    /**
     * @Description: 消费者配置
     */
    public Map<String, Object> consumerConfigs() {

        Map<String, Object> props = new HashMap<String, Object>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, configuration.getBootstrapServers());
        // 消费者组
        props.put(ConsumerConfig.GROUP_ID_CONFIG, configuration.getConsumerGroupId());
        // 自动位移提交
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        // 自动位移提交间隔时间
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 100);
        // 消费组失效超时时间
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 10000);
        // 位移丢失和位移越界后的恢复起始位置
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        // key 和 value 的反序列化"org.apache.kafka.common.serialization.StringDeserializer"
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return props;
    }

    /**
     * @Description: 消费者工厂
     */
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    /**
     * @Description: kafka 监听容器工厂
     */
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        // 设置消费者工厂
        factory.setConsumerFactory(consumerFactory());
        // 要创建的消费者数量(10 个线程并发处理)
        factory.setConcurrency(10);

        return factory;
    }

}
