package bme.cateringunitmonitor.userservice.service;

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
    private final Queue queue;

    @Autowired
    public EventSender(RabbitTemplate template, Queue queue) {
        log.info("EventSender service created.");
        this.template = template;
        this.queue = queue;
        //Send initial event to create queue
        GenericEvent event = new GenericEvent("InitQueue", "queueName", queue.getName());
        this.template.convertAndSend(queue.getName(), event.getMessage());
    }

    public void send(String message) {
        this.template.convertAndSend(queue.getName(), message);
    }
}
