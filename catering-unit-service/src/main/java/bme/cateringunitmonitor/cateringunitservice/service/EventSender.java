package bme.cateringunitmonitor.cateringunitservice.service;

import bme.cateringunitmonitor.utils.ProdProfileCondition;
import bme.cateringunitmonitor.utils.amqp.GenericEvent;
import lombok.extern.slf4j.Slf4j;
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
        GenericEvent event = new GenericEvent("InitQueue", "queueName", "catering");
        this.template.convertAndSend("fanout.exchange.catering", event.getMessage());
    }

    public void send(String message) {
        this.template.convertAndSend("fanout.exchange.catering","", message);
    }
}
