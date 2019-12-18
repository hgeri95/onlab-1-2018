package bme.cateringunitmonitor.notification.service;

import bme.cateringunitmonitor.notification.SubscriptionRepository;
import bme.cateringunitmonitor.utils.amqp.EventTypes;
import bme.cateringunitmonitor.utils.amqp.GenericEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static bme.cateringunitmonitor.utils.amqp.QueueNames.*;

@Service
@RabbitListener(queues = {USER_TO_NOTIFICATION_QUEUE, CATERING_TO_NOTIFICATION_QUEUE})
@Slf4j
@Transactional
public class EventReceiver {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @RabbitHandler
    public void receiveDeleteUser(String event) {
        log.info("Event message received: {}", event);
        GenericEvent genericEvent = new GenericEvent();
        genericEvent.deserializeMessage(event);

        if (genericEvent.getEventName().equals(EventTypes.DELETE_CATERING_EVENT)) {
            String cateringUnitName = genericEvent.getEventParameter();
            int deletedSubscriptionCount = subscriptionRepository.deleteAllByCateringUnitName(cateringUnitName);
            log.info("Deleted subscriptions: {} by catering name: {}", deletedSubscriptionCount, cateringUnitName);
        }
        if (genericEvent.getEventName().equals(EventTypes.DELETE_USER_EVENT)) {
            String username = genericEvent.getEventParameter();
            int deletedSubscriptionCount = subscriptionRepository.deleteAllByUsername(username);
            log.info("Deleted subscriptions: {} by username: {}", deletedSubscriptionCount, username);
        }
    }
}

