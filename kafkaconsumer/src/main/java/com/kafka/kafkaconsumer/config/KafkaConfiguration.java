package com.kafka.kafkaconsumer.config;
import com.kafka.kafkaconsumer.model.User;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.errors.TimeoutException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import java.util.HashMap;
import java.util.Map;


@EnableKafka
@Configuration
public class KafkaConfiguration {

    @Bean
    public ConsumerFactory<String, User> userConsumerFactory(){
        //Create HashMap to store configuration details
        Map<String, Object> config = new HashMap<>();
        //Configure the port where Kafka is advertised
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_json");
        //Configure value deserializer type. This is the ErrorHandlingDeserializer to handle deserialization errors
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        //Configure key deserializer type. This is the ErrorHandlingDeserializer to handle deserialization errors
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        //Configuring ErrorHandlingDeserializer key data type
        config.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        //Configuring ErrorHandlingDeserializer value data type
        config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
        //define model for deserialize data
        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.kafka.kafkaconsumer.model.User");
        config.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, "false");
        //defines which models to trust for deserializing data
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        return new DefaultKafkaConsumerFactory<>(config);
    }

    private RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(retryPolicy());
        return retryTemplate;
    }

    private SimpleRetryPolicy retryPolicy() {
        //Defines policy for retries if consumption fails
        Map<Class<? extends Throwable>, Boolean> exceptionMap = new HashMap<>();

        // the boolean value in the map determines whether exception should be retried
        exceptionMap.put(IllegalArgumentException.class, false);
        exceptionMap.put(TimeoutException.class, true);
        exceptionMap.put(ListenerExecutionFailedException.class, false);

        return new SimpleRetryPolicy(2, exceptionMap, true);
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,User> userKafkaListenerFactory(){
        ConcurrentKafkaListenerContainerFactory<String,User> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(userConsumerFactory());
        factory.setRetryTemplate(retryTemplate());

        return factory;
    }
}
