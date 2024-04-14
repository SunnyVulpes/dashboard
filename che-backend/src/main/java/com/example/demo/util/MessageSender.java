package com.example.demo.util;
import com.alibaba.fastjson.JSON;
import com.example.demo.dto.PhotoDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MessageSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    public void sendPhoto(PhotoDto poemDto){
        String routingKey="photo";
        rabbitTemplate.convertAndSend("amq.direct",routingKey, JSON.toJSONString(poemDto));
    }
}

