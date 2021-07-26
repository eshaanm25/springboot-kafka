package com.kafka.kafkaconsumer.listener;


import com.kafka.kafkaconsumer.model.User;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "Kafka_Example", groupId="group_json", containerFactory = "userKafkaListenerFactory")
    public void consumeJson(User user){
            System.out.println("Consumed JSON Message" + user);
            System.out.println(user.getId());
    }
}
