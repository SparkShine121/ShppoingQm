package com.rabbitmq;

import com.redis.RedisService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;


//@Component
public class MQSender {

    @Autowired
    AmqpTemplate amqpTemplate;

    //    public void send(Object message){
//        String msg= RedisService.beanToString(message);
//        amqpTemplate.convertAndSend(MQConfig.QUEUE,msg);
//    }
    //发送秒杀消息
    public void sendTopic(Object message) {
        String msg = RedisService.beanToString(message);
        amqpTemplate.convertAndSend(MQConfig.MIAOSHA_EXCHANGE, "topic.key1", msg);
//        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key2", msg+"2");
    }
}
