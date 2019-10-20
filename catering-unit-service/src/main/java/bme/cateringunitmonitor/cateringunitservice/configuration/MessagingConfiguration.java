package bme.cateringunitmonitor.cateringunitservice.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static bme.cateringunitmonitor.utils.amqp.QueueNames.CATERING_QUEUE_NAME;

@Configuration
@Slf4j
public class MessagingConfiguration {

    @Bean
    public Queue queue() {
        log.info("Create new queue with name: {}", CATERING_QUEUE_NAME);
        return new Queue(CATERING_QUEUE_NAME, false);
    }
}
