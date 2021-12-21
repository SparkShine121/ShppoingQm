package com.rabbitmq;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    public static final String MIAOSHA_QUEUE = "miaosha.queue";
    public static final String MIAOSHA_EXCHANGE = "MiaoshaExchage";
    public static final String QUEUE = "queue";
    public static final String TOPIC_QUEUE1 = "topic.queue1";
    public static final String TOPIC_QUEUE2 = "topic.queue2";
    public static final String HEADER_QUEUE = "header.queue";
    public static final String FANOUT_EXCHANGE = "fanoutxchage";
    public static final String HEADERS_EXCHANGE = "headersExchage";

    /**
     * Direct模式 交换机Exchange
     */
    @Bean
    public Queue miaoshaqueue() {
        return new Queue(MIAOSHA_QUEUE, true);
    }

    @Bean
    public TopicExchange miaoshaExchange() {
        return new TopicExchange(MIAOSHA_EXCHANGE);
    }

    //秒杀队列与交换机绑定
    @Bean
    public Binding topicBinding1() {
        return BindingBuilder.bind(miaoshaqueue()).to(miaoshaExchange()).with("topic.key1");
    }

}
