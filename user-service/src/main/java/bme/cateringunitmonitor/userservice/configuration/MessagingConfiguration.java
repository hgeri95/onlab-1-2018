package bme.cateringunitmonitor.userservice.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static bme.cateringunitmonitor.utils.amqp.QueueNames.*;
import static org.springframework.amqp.core.BindingBuilder.bind;

@Configuration
@Slf4j
public class MessagingConfiguration {

    @Bean
    public Declarables fanoutBindings() {
        Queue queueNotification = new Queue(USER_TO_NOTIFICATION_QUEUE, false);
        Queue queueRatings = new Queue(USER_TO_RATING_QUEUE, false);
        FanoutExchange fanoutExchange = new FanoutExchange("fanout.exchange.user");

        return new Declarables(
                queueNotification,
                queueRatings,
                fanoutExchange,
                bind(queueNotification).to(fanoutExchange),
                BindingBuilder.bind(queueRatings).to(fanoutExchange)
        );
    }

    @Bean
    public AmqpAdmin amqpAdmin(RabbitTemplate template) {
        return new RabbitAdmin(template);
    }

  /*  @Bean
    public Queue queue() {
        log.info("Create new queue with name: {}", USER_QUEUE_NAME);
        return new Queue(USER_QUEUE_NAME, false);
    }*/
}
