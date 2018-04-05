package bme.cateringunitmonitor.userservice;

import bme.cateringunitmonitor.userservice.controller.AuthController;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthTest {

    @Autowired
    AuthController authController;

    @Test
    public void contextLoads() {
        Assert.assertNotNull(authController);
    }

    //TODO Add tests
}
