package bme.cateringunitmonitor.notification.service;

import bme.cateringunitmonitor.api.dto.UserInfoBulkRequest;
import bme.cateringunitmonitor.api.dto.UserInfoDTO;
import bme.cateringunitmonitor.api.remoting.controller.IUserController;
import bme.cateringunitmonitor.notification.dto.NotificationBulkRequest;
import bme.cateringunitmonitor.notification.dto.NotificationDirectRequest;
import bme.cateringunitmonitor.notification.dto.NotificationRequest;
import bme.cateringunitmonitor.notification.dto.NotificationResponse;
import bme.cateringunitmonitor.security.UserAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NotificationService {

    @Autowired
    private IUserController userController;

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public CompletableFuture<NotificationResponse> sendMailForUser(NotificationRequest notificationRequest) {
        log.debug("Send email notification for user: {}", notificationRequest.getUsername());

        UserAuthentication userAuthentication = (UserAuthentication) SecurityContextHolder.getContext().getAuthentication();
        log.info("Authentication: {}", userAuthentication);
        UserInfoDTO userInfo = userController.getUserInfoByUsername(notificationRequest.getUsername());

        SimpleMailMessage message = buildMessage(userInfo.getEmail(),
                notificationRequest.getSubject(), notificationRequest.getMessage());
        log.debug("Message to send: {}", message.toString());
        mailSender.send(message);

        return CompletableFuture.completedFuture(
                new NotificationResponse(Collections.singletonList(notificationRequest.getUsername())));
    }

    @Async
    public CompletableFuture<NotificationResponse> sendMailForUsers(NotificationBulkRequest notificationRequest) {
        log.debug("Send email notification for users: {}", notificationRequest.getUsernames());

        List<UserInfoDTO> userinfos = userController
                .getUserInfosByUsernames(new UserInfoBulkRequest(new ArrayList<>(notificationRequest.getUsernames())));

        List<String> usernamesToSend = new ArrayList<>(notificationRequest.getUsernames());
        for (UserInfoDTO userInfo : userinfos) {
            SimpleMailMessage message = buildMessage(
                    userInfo.getEmail(), notificationRequest.getSubject(), notificationRequest.getMessage());
            log.debug("Message to send: {}", message.toString());
            mailSender.send(message);
            usernamesToSend.remove(userInfo.getUsername());
        }

        log.warn("The following users has no userinfo: {}", usernamesToSend);
        List<String> usernamesSend = userinfos.stream().map(i -> i.getUsername()).collect(Collectors.toList());

        return CompletableFuture.completedFuture(new NotificationResponse(usernamesSend));
    }

    @Async
    public CompletableFuture<String> sendDirectMail(NotificationDirectRequest notificationRequest) {
        log.debug("Send email for address: {}", notificationRequest.getEmail());

        SimpleMailMessage message = buildMessage(notificationRequest.getEmail(),
                notificationRequest.getSubject(), notificationRequest.getMessage());
        log.debug("Message to send: {}", message.toString());
        mailSender.send(message);

        return CompletableFuture.completedFuture("Message sent to " + notificationRequest.getEmail() + ".");
    }

    private SimpleMailMessage buildMessage(String emailAddress, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(emailAddress);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        return mailMessage;
    }
}
