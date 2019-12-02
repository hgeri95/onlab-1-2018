package bme.cateringunitmonitor.notification.service;

import bme.cateringunitmonitor.api.Gender;
import bme.cateringunitmonitor.api.dto.UserInfoDTO;
import bme.cateringunitmonitor.api.remoting.controller.IUserController;
import bme.cateringunitmonitor.notification.SubscriptionRepository;
import bme.cateringunitmonitor.notification.dto.NotificationBulkRequest;
import bme.cateringunitmonitor.notification.dto.NotificationDirectRequest;
import bme.cateringunitmonitor.notification.dto.NotificationRequest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Arrays;

import static bme.cateringunitmonitor.utils.Profiles.TEST_PROFILE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles(TEST_PROFILE)
@RunWith(SpringRunner.class)
public class NotificationServiceTest {

    private static final String username = "username";
    private static final String[] usernames = {"username1", "username2", "username3"};

    @Configuration
    static class ContextConfiguration {
        @Bean
        IUserController userController() {
            IUserController userController = mock(IUserController.class);
            when(userController.getUserInfoByUsername(any()))
                    .thenReturn(new UserInfoDTO(username, "f", "c", "b@b.com", null, Gender.MALE));
            when(userController.getUserInfosByUsernames(any()))
                    .thenReturn(Arrays.asList(
                            new UserInfoDTO(usernames[0], "f", "c", "c@c.com", null, Gender.MALE),
                            new UserInfoDTO(usernames[1], "f", "c", "d@d.com", null, Gender.MALE)
                            )
                    );

            return userController;
        }

        @Bean
        SubscriptionRepository subscriptionRepository() {
            return mock(SubscriptionRepository.class);
        }

        @Bean
        NotificationService notificationService() {
            return new NotificationService();
        }

        @Bean
        JavaMailSender javaMailSender() {
            JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
            javaMailSender.setHost("localhost");
            javaMailSender.setPort(2526);
            return javaMailSender;
        }
    }

    @Autowired
    private NotificationService notificationService;

    @Rule
    public SmtpServerRule smtpServerRule = new SmtpServerRule(2526);

    @Test
    public void testSimpleEmailSending() throws MessagingException, IOException {
        final String subject = "sub";
        final String message = "mess";
        NotificationDirectRequest notificationRequest = new NotificationDirectRequest("a@a.com", subject, message);
        notificationService.sendDirectMail(notificationRequest);

        assertTrue(smtpServerRule.getGreenMail().waitForIncomingEmail(5000, 1));
        Message[] messages = smtpServerRule.getMessages();
        assertEquals(subject, messages[0].getSubject());
        String receivedMessage = String.valueOf(messages[0].getContent());
        assertTrue(receivedMessage.contains(message));
    }

    @Test
    public void testSendMailForUser() throws MessagingException, IOException {
        final String subject = "subject";
        final String message = "message";
        NotificationRequest notificationRequest = new NotificationRequest(username, subject, message);
        notificationService.sendMailForUser(notificationRequest);

        assertTrue(smtpServerRule.getGreenMail().waitForIncomingEmail(5000, 1));
        Message[] messages = smtpServerRule.getMessages();
        assertEquals(1, messages.length);
        assertEquals(subject, messages[0].getSubject());
        assertTrue(String.valueOf(messages[0].getContent()).contains(message));
    }

    @Test
    public void testSendMailForMultipleUsers() {
        final String subject = "subsub";
        final String message = "Hello world!";
        NotificationBulkRequest notificationRequest = new NotificationBulkRequest(
                Arrays.asList(usernames), subject, message);
        notificationService.sendMailForUsers(notificationRequest);

        assertTrue(smtpServerRule.getGreenMail().waitForIncomingEmail(5000, 2));
        Message[] messages = smtpServerRule.getMessages();
        assertEquals(2, messages.length);
    }
}
