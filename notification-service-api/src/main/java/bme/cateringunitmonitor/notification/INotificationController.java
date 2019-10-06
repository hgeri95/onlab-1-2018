package bme.cateringunitmonitor.notification;

import bme.cateringunitmonitor.api.Role;
import bme.cateringunitmonitor.feign.FeignConfiguration;
import bme.cateringunitmonitor.notification.dto.NotificationBulkRequest;
import bme.cateringunitmonitor.notification.dto.NotificationDirectRequest;
import bme.cateringunitmonitor.notification.dto.NotificationRequest;
import bme.cateringunitmonitor.notification.dto.NotificationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@FeignClient(name = "NotificationController", url = "${notificationServiceUrl}", configuration = FeignConfiguration.class)
public interface INotificationController {

    String BASE_PATH = "api/v1/notification";

    @PostMapping(BASE_PATH + "/email")
    @Secured({Role.Values.ROLE_OWNER, Role.Values.ROLE_USER, Role.Values.ROLE_TECHNICAL})
    public CompletableFuture<NotificationResponse> sendMail(
            @RequestBody @Valid NotificationRequest notificationRequest);

    @PostMapping(BASE_PATH + "/email/bulk")
    @Secured({Role.Values.ROLE_OWNER, Role.Values.ROLE_USER, Role.Values.ROLE_TECHNICAL})
    public CompletableFuture<NotificationResponse> sendBulkMails(
            @RequestBody @Valid NotificationBulkRequest notificationRequest);

    @PostMapping(BASE_PATH + "/email/direct")
    @Secured({Role.Values.ROLE_OWNER, Role.Values.ROLE_USER, Role.Values.ROLE_TECHNICAL})
    public CompletableFuture<String> sendDirectMail(@RequestBody @Valid NotificationDirectRequest notificationRequest);
}
