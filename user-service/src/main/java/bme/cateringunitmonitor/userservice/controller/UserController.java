package bme.cateringunitmonitor.userservice.controller;

import bme.cateringunitmonitor.entities.user.entity.User;
import bme.cateringunitmonitor.userservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.management.relation.Role;
import java.util.Collections;

@RestController
@RequestMapping("/users")
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/sign-up")
    @Secured("ROLE_ADMIN") //TODO Anybody
    public void signUp(@RequestBody User user) {
        userService.create(user);
    }

    @PostConstruct
    public void createDefaultAdminUser() {
        userService.create(new User("admin", "12345", Collections.singletonList("ROLE_ADMIN")));
        logger.info("ADMIN USER CREATED: admin 12345");
    }
    //TODO GetUserInfo, SetUserInfo
}
