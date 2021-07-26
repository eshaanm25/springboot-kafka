package com.esh.kafka.kafkaproducer.resource;

import com.esh.kafka.kafkaproducer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("kafka")
public class UserResource {

    @Autowired
    KafkaTemplate<String, User> kafkaTemplate;
    private static final String TOPIC = "Kafka_Example";

    @PostMapping(
            value = "/add", consumes = "application/json", produces = "application/json")
    public String createPerson(@RequestBody User user) {
        kafkaTemplate.send(TOPIC, new User(user.getName(),user.getRole(), user.getCompany(), user.getId()));
        System.out.println(user.getName());
        return "Published Successfully";
    }
}
