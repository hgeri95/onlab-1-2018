package bme.cateringunitmonitor.userservice;

import bme.cateringunitmonitor.api.dao.User;
import bme.cateringunitmonitor.userservice.repository.UserRepository;
import bme.cateringunitmonitor.userservice.service.UserService;
import org.assertj.core.util.Lists;
import org.junit.Assert;
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

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(UserServiceTestConfig.class)
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
        User user = new User("test", "123", Lists.emptyList());
        Assert.assertEquals(user, userRepository.save(user));

        Assert.assertNotNull(userRepository.findByUsername(user.getUsername()));
        Assert.assertNull(userRepository.findByUsername("asd"));

        Assert.assertEquals(1, userRepository.deleteByUsername(user.getUsername()));
        Assert.assertNull(userRepository.findByUsername(user.getUsername()));
    }

    @Test
    public void testUserServiceMethods() {
        User user = new User("dummyUser", "123", Lists.emptyList());
        userService.create(user);

        Assert.assertNotNull(userRepository.findByUsername(user.getUsername()));

        expectedException.expect(BadCredentialsException.class);
        userService.login(new User(user.getUsername(), "badPassword", Lists.emptyList()));

        Assert.assertEquals(user.getUsername(),
                userService.login(new User(user.getUsername(), "123", Lists.emptyList())).getUsername());
    }
}
