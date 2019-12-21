package bme.cateringunitmonitor.notification.controller;

import bme.cateringunitmonitor.notification.INotificationController;
import bme.cateringunitmonitor.notification.dto.*;
import bme.cateringunitmonitor.notification.exception.NotificationServiceException;
import bme.cateringunitmonitor.notification.service.NotificationService;
import bme.cateringunitmonitor.security.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
public class NotificationController implements INotificationController {

    @Autowired
    private NotificationService notificationService;

    @Override
    public NotificationResponse sendMail(@Valid NotificationRequest notificationRequest) {
        return notificationService.sendMailForUser(notificationRequest);
    }

    @Override
    public NotificationResponse sendBulkMails(@Valid NotificationBulkRequest notificationRequest) {
        return notificationService.sendMailForUsers(notificationRequest);
    }

    @Override
    public String sendDirectMail(@Valid NotificationDirectRequest notificationRequest) {
        return notificationService.sendDirectMail(notificationRequest);
    }

    @Override
    public NotificationResponse sendSubscribed(String cateringUnitName, @Valid NotificationSubscribedRequest notificationRequest) {
        try {
            return notificationService.sendSubscribed(cateringUnitName, notificationRequest);
        } catch (NotificationServiceException ex) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, ex.getMessage());
        }
    }

    @Override
    public ResponseEntity<SubscriptionResponse> subscribe(String cateringUnitName) {
        String username = SecurityUtil.getActiveUser();
        try {
            return ResponseEntity.ok(notificationService.subscribe(username, cateringUnitName));
        } catch (NotificationServiceException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @Override
    public ResponseEntity<SubscriptionResponse> unsubscribe(String cateringUnitName) {
        String username = SecurityUtil.getActiveUser();
        try {
            return ResponseEntity.ok(notificationService.unsubscribe(username, cateringUnitName));
        } catch (NotificationServiceException ex) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, ex.getMessage());
        }
    }

    @Override
    public ResponseEntity<SubscriptionResponse> isSubscribed(String cateringUnitName) {
        String username = SecurityUtil.getActiveUser();
        try {
            return ResponseEntity.ok(notificationService.isSubscribed(username, cateringUnitName));
        } catch (NotificationServiceException ex) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, ex.getMessage());
        }
    }
}
