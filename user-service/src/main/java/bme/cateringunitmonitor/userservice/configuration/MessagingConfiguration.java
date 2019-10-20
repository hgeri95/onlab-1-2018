package bme.cateringunitmonitor.userservice.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static bme.cateringunitmonitor.utils.amqp.QueueNames.USER_QUEUE_NAME;

@Configuration
@Slf4j
public class MessagingConfiguration {

    @Bean
    public Queue queue() {
        log.info("Create new queue with name: {}", USER_QUEUE_NAME);
        return new Queue(USER_QUEUE_NAME, false);
    }
}
