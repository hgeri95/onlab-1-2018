package bme.cateringunitmonitor.rating.service;

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
@RabbitListener(queues = {USER_TO_RATING_QUEUE, CATERING_TO_RATING_QUEUE})
@Slf4j
@Transactional
public class EventReceiver {

    @Autowired
    private RatingService ratingService;

    @RabbitHandler
    public void receiveDeleteUser(String event) {
        log.info("Event message received: {}", event);
        GenericEvent genericEvent = new GenericEvent();
        genericEvent.deserializeMessage(event);

        if (genericEvent.getEventName().equals(EventTypes.DELETE_CATERING_EVENT)) {
            ratingService.deleteRatingsByCateringUnitName(genericEvent.getEventParameter());
        }
        if (genericEvent.getEventName().equals(EventTypes.DELETE_USER_EVENT)) {
            ratingService.deleteRatingsByUsername(genericEvent.getEventParameter());
        }
    }
}
