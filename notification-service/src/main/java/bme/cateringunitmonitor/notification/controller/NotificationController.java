package bme.cateringunitmonitor.notification.controller;

import bme.cateringunitmonitor.notification.INotificationController;
import bme.cateringunitmonitor.notification.dto.NotificationBulkRequest;
import bme.cateringunitmonitor.notification.dto.NotificationDirectRequest;
import bme.cateringunitmonitor.notification.dto.NotificationRequest;
import bme.cateringunitmonitor.notification.dto.NotificationResponse;
import bme.cateringunitmonitor.notification.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
public class NotificationController implements INotificationController {

    @Autowired
    private NotificationService notificationService;

    @Override
    public CompletableFuture<NotificationResponse> sendMail(@Valid NotificationRequest notificationRequest) {
        return notificationService.sendMailForUser(notificationRequest);
    }

    @Override
    public CompletableFuture<NotificationResponse> sendBulkMails(@Valid NotificationBulkRequest notificationRequest) {
        return notificationService.sendMailForUsers(notificationRequest);
    }

    @Override
    public CompletableFuture<String> sendDirectMail(@Valid NotificationDirectRequest notificationRequest) {
        return notificationService.sendDirectMail(notificationRequest);
    }
}
