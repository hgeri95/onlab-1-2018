package bme.cateringunitmonitor.userservice;

import bme.cateringunitmonitor.api.Gender;
import bme.cateringunitmonitor.api.dto.UserDTO;
import bme.cateringunitmonitor.api.dto.UserInfoDTO;
import bme.cateringunitmonitor.api.dto.UserInfoRequest;
import bme.cateringunitmonitor.api.dto.UserRequest;
import bme.cateringunitmonitor.api.exception.UserServiceException;
import bme.cateringunitmonitor.userservice.dao.UserDAO;
import bme.cateringunitmonitor.userservice.repository.UserRepository;
import bme.cateringunitmonitor.userservice.service.UserService;
import org.assertj.core.util.Lists;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import({UserServiceTestConfig.class})
public class UserServiceTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testUserRepositoryMethods() {
        UserDAO user = new UserDAO("test", "123", new HashSet<>());
        assertEquals(user, userRepository.save(user));

        assertNotNull(userRepository.findByUsername(user.getUsername()));
        assertNull(userRepository.findByUsername("asd"));

        assertEquals(1, userRepository.deleteByUsername(user.getUsername()));
        assertNull(userRepository.findByUsername(user.getUsername()));
    }

    @Test
    public void testUserServiceMethods() throws UserServiceException {
        UserRequest user = new UserRequest("dummyUser", "123", Lists.emptyList());
        userService.create(user);

        assertNotNull(userRepository.findByUsername(user.getUsername()));

        expectedException.expect(BadCredentialsException.class);
        userService.login(new UserDTO(user.getUsername(), "badPassword", Lists.emptyList()));

        assertEquals(user.getUsername(),
                userService.login(new UserDTO(user.getUsername(), "123", Lists.emptyList())).getUsername());
    }

    @Test
    public void testUserInfoCrud() throws UserServiceException {
        String username = "testUser";
        UserInfoRequest userInfo = new UserInfoRequest(username, "Test User", "San Diego",
                "a@a.com", LocalDate.now(), Gender.MALE);
        UserInfoDTO savedUserInfo = userService.saveUserInfo(userInfo);
        assertNotNull(savedUserInfo);

        String city = "Miami";
        userInfo.setCity(city);
        savedUserInfo = userService.updateUserInfo(userInfo);
        assertEquals(city, savedUserInfo.getCity());

        savedUserInfo = userService.getUserInfo(username);
        assertNotNull(savedUserInfo);
    }

    @Test
    public void testGetBulkUsers() throws UserServiceException {
        String user1 = "user1";
        String user2 = "user2";
        String user3 = "user3";
        UserInfoRequest userInfoRequest1 = new UserInfoRequest(
                user1, "f", "c", "a@a.com", LocalDate.now(), Gender.MALE);
        UserInfoRequest userInfoRequest2 = new UserInfoRequest(
                user2, "f", "c", "a@a.com", LocalDate.now(), Gender.MALE);
        UserInfoRequest userInfoRequest3 = new UserInfoRequest(
                user3, "f", "c", "a@a.com", LocalDate.now(), Gender.MALE);
        userService.saveUserInfo(userInfoRequest1);
        userService.saveUserInfo(userInfoRequest2);
        userService.saveUserInfo(userInfoRequest3);

        List<String> usernamesToFind = Arrays.asList(user1, user2, "user4");
        List<UserInfoDTO> userInfos = userService.getUserInfos(usernamesToFind);

        assertEquals(2, userInfos.size());
    }
}
