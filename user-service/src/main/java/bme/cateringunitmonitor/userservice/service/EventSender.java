package bme.cateringunitmonitor.userservice.service;

import bme.cateringunitmonitor.utils.ProdProfileCondition;
import bme.cateringunitmonitor.utils.amqp.GenericEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

@Service
@Conditional(value = ProdProfileCondition.class)
@Slf4j
public class EventSender {

    private final RabbitTemplate template;

    @Autowired
    public EventSender(RabbitTemplate rabbitTemplate) {
        log.info("EventSender service created.");
        this.template = rabbitTemplate;
        //Send initial event to create queue
        GenericEvent event = new GenericEvent("InitQueue", "queueName", "user");
        this.template.convertAndSend("fanout.exchange.user", event.getMessage());
    }

    public void send(String message) {
        this.template.convertAndSend("fanout.exchange.user", "",message);
    }
}
