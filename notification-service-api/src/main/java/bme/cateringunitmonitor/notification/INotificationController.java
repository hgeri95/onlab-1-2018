package bme.cateringunitmonitor.notification;

import bme.cateringunitmonitor.api.Role;
import bme.cateringunitmonitor.feign.FeignConfiguration;
import bme.cateringunitmonitor.notification.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@FeignClient(name = "NotificationController", url = "${notificationServiceUrl}", configuration = FeignConfiguration.class)
public interface INotificationController {

    String BASE_PATH = "api/v1/notification";

    @PostMapping(BASE_PATH + "/email")
    @Secured({Role.Values.ROLE_OWNER, Role.Values.ROLE_USER, Role.Values.ROLE_TECHNICAL})
    public NotificationResponse sendMail(
            @RequestBody @Valid NotificationRequest notificationRequest);

    @PostMapping(BASE_PATH + "/email/bulk")
    @Secured({Role.Values.ROLE_OWNER, Role.Values.ROLE_USER, Role.Values.ROLE_TECHNICAL})
    public NotificationResponse sendBulkMails(
            @RequestBody @Valid NotificationBulkRequest notificationRequest);

    @PostMapping(BASE_PATH + "/email/direct")
    @Secured({Role.Values.ROLE_OWNER, Role.Values.ROLE_USER, Role.Values.ROLE_TECHNICAL})
    public String sendDirectMail(@RequestBody @Valid NotificationDirectRequest notificationRequest);

    @PutMapping(BASE_PATH + "/email/subscribed/{cateringUnitName}")
    @Secured({Role.Values.ROLE_OWNER, Role.Values.ROLE_ADMIN, Role.Values.ROLE_TECHNICAL})
    public NotificationResponse sendSubscribed(@PathVariable("cateringUnitName") String cateringUnitName,
    @RequestBody @Valid NotificationSubscribedRequest notificationRequest);

    @PutMapping(BASE_PATH + "/subscribe/{cateringUnitName}")
    @Secured({Role.Values.ROLE_ADMIN, Role.Values.ROLE_USER})
    public ResponseEntity<SubscriptionResponse> subscribe(@PathVariable("cateringUnitName") String cateringUnitName);

    @PutMapping(BASE_PATH + "/unsubscribe/{cateringUnitName}")
    @Secured({Role.Values.ROLE_ADMIN, Role.Values.ROLE_USER})
    public ResponseEntity<SubscriptionResponse> unsubscribe(@PathVariable("cateringUnitName") String cateringUnitName);

    @GetMapping(BASE_PATH + "/isSubscribed/{cateringUnitName}")
    @Secured({Role.Values.ROLE_ADMIN, Role.Values.ROLE_USER})
    public ResponseEntity<SubscriptionResponse> isSubscribed(@PathVariable("cateringUnitName") String cateringUnitName);
}
