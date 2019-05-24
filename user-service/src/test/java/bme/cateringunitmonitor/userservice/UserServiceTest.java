package bme.cateringunitmonitor.userservice;

import bme.cateringunitmonitor.api.Gender;
import bme.cateringunitmonitor.api.dto.UserDTO;
import bme.cateringunitmonitor.api.dto.UserInfoDTO;
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
import java.util.HashSet;

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
    public void testUserServiceMethods() {
        UserDTO user = new UserDTO("dummyUser", "123", Lists.emptyList());
        userService.create(user);

        assertNotNull(userRepository.findByUsername(user.getUsername()));

        expectedException.expect(BadCredentialsException.class);
        userService.login(new UserDTO(user.getUsername(), "badPassword", Lists.emptyList()));

        assertEquals(user.getUsername(),
                userService.login(new UserDTO(user.getUsername(), "123", Lists.emptyList())).getUsername());
    }

    @Test
    public void testUserInfoCrud() {
        String username = "testUser";
        UserInfoDTO userInfo = new UserInfoDTO(username, "Test User", "San Diego",
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
}
