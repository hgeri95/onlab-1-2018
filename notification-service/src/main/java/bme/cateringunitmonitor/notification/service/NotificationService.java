package bme.cateringunitmonitor.notification.service;

import bme.cateringunitmonitor.api.dto.UserInfoDTO;
import bme.cateringunitmonitor.api.remoting.controller.IUserController;
import bme.cateringunitmonitor.notification.SubscriptionRepository;
import bme.cateringunitmonitor.notification.dao.SubscriptionDAO;
import bme.cateringunitmonitor.notification.dto.*;
import bme.cateringunitmonitor.notification.exception.NotificationServiceException;
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
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NotificationService {

    @Autowired
    private IUserController userController;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public NotificationResponse sendMailForUser(NotificationRequest notificationRequest) {
        log.debug("Send email notification for user: {}", notificationRequest.getUsername());

        UserAuthentication userAuthentication = (UserAuthentication) SecurityContextHolder.getContext().getAuthentication();
        log.info("Authentication: {}", userAuthentication);
        UserInfoDTO userInfo = userController.getUserInfoByUsername(notificationRequest.getUsername());

        SimpleMailMessage message = buildMessage(userInfo.getEmail(),
                notificationRequest.getSubject(), notificationRequest.getMessage());
        log.debug("Message to send: {}", message.toString());
        mailSender.send(message);

        return new NotificationResponse(Collections.singletonList(notificationRequest.getUsername()));
    }

    public NotificationResponse sendMailForUsers(NotificationBulkRequest notificationRequest) {
        log.debug("Send email notification for users: {}", notificationRequest.getUsernames());

        String[] userNames = notificationRequest.getUsernames().stream().toArray(String[] :: new);
        List<UserInfoDTO> userinfos = userController
                .getUserInfosByUsernames(userNames);

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

        return new NotificationResponse(usernamesSend);
    }

    public String sendDirectMail(NotificationDirectRequest notificationRequest) {
        log.debug("Send email for address: {}", notificationRequest.getEmail());

        SimpleMailMessage message = buildMessage(notificationRequest.getEmail(),
                notificationRequest.getSubject(), notificationRequest.getMessage());
        log.debug("Message to send: {}", message.toString());
        mailSender.send(message);

        return "Message sent to " + notificationRequest.getEmail() + ".";
    }

    public SubscriptionResponse subscribe(String username, String cateringUnitName) throws NotificationServiceException {
        log.info("Create new subscription for user: {}, for catering: {}", username, cateringUnitName);
        Optional<SubscriptionDAO> existingSubscription = subscriptionRepository
                .findByCateringUnitNameAndUsername(cateringUnitName, username);
        if (existingSubscription.isPresent()) {
            log.debug("Subscription already exists: {}", existingSubscription.get());
            throw new NotificationServiceException("Subscription already exists!");
        } else {
            SubscriptionDAO subscription = new SubscriptionDAO(cateringUnitName, username);
            log.debug("Create new subscription: {}", subscription);
            SubscriptionDAO savedSubscription = subscriptionRepository.save(subscription);
            return new SubscriptionResponse(savedSubscription.getUsername(), savedSubscription.getCateringUnitName());
        }
    }

    public SubscriptionResponse unsubscribe(String username, String cateringUnitName) throws NotificationServiceException {
        log.info("Delete subscription for user: {}, for catering: {}" ,username, cateringUnitName);
        Optional<SubscriptionDAO> existingSubscription = subscriptionRepository
                .findByCateringUnitNameAndUsername(cateringUnitName, username);
        if (existingSubscription.isPresent()) {
            subscriptionRepository.delete(existingSubscription.get());
            log.debug("Subscription deleted");
            return new SubscriptionResponse(username, cateringUnitName);
        } else {
            log.debug("No subscription to delete");
            throw new NotificationServiceException("Subscription not exists!");
        }
    }

    public SubscriptionResponse isSubscribed(String username, String cateringUnitName) throws NotificationServiceException {
        log.info("Is subscription exists for user: {}, for catering: {} ?" ,username, cateringUnitName);
        Optional<SubscriptionDAO> existingSubscription = subscriptionRepository
                .findByCateringUnitNameAndUsername(cateringUnitName, username);
        if (existingSubscription.isPresent()) {
            log.debug("Subscription exists");
            return new SubscriptionResponse(existingSubscription.get().getUsername(), existingSubscription.get().getCateringUnitName());
        } else {
            throw new NotificationServiceException("Subscription not exists!");
        }
    }

    public NotificationResponse sendSubscribed(String cateringUnitName, NotificationSubscribedRequest notificationRequest) throws NotificationServiceException {
        log.info("Send notification for subscribed users for catering: {}", cateringUnitName);
        List<SubscriptionDAO> subscriptions = subscriptionRepository.findAllByCateringUnitName(cateringUnitName);
        if (!subscriptions.isEmpty()) {
            List<String> usernames = subscriptions.stream().map(SubscriptionDAO::getUsername).collect(Collectors.toList());
            NotificationBulkRequest notificationBulkRequest = new NotificationBulkRequest(usernames, notificationRequest.getSubject(), notificationRequest.getMessage());
            return sendMailForUsers(notificationBulkRequest);
        } else {
            throw new NotificationServiceException("No subscribed users!");
        }
    }

    private SimpleMailMessage buildMessage(String emailAddress, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(emailAddress);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        return mailMessage;
    }
}
