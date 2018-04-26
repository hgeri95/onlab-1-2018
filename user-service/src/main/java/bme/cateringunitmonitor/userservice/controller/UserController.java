package bme.cateringunitmonitor.userservice.controller;

import bme.cateringunitmonitor.entities.user.api.UserInfoRequest;
import bme.cateringunitmonitor.entities.user.entity.Role;
import bme.cateringunitmonitor.entities.user.entity.User;
import bme.cateringunitmonitor.entities.user.entity.UserInfo;
import bme.cateringunitmonitor.userservice.security.SecurityUtil;
import bme.cateringunitmonitor.userservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostConstruct
    public void createDefaultAdminUser() {
        userService.create(new User("admin", "12345", Collections.singletonList(Role.ROLE_ADMIN.toString())));
        logger.info("\n////////\n////////\nADMIN USER CREATED: admin 12345\n////////\n////////");
    }

    @PostMapping("/sign-up")
    public User signUp(@RequestBody User user) {
        logger.debug("Sign up new user: {}", user);
        return userService.create(user);
    }

    @GetMapping("/get-available-roles")
    public List<String> getAvailableRoles() {
        return Role.getAllRoles();
    }

    @PostMapping("/userinfo")
    @Secured({Role.Values.ROLE_OWNER, Role.Values.ROLE_USER})
    public UserInfo setUserInfo(@RequestBody UserInfoRequest userInfoRequest) {
        logger.debug("Set user info: {}", userInfoRequest);
        String activeUser = SecurityUtil.getActiveUser();
        UserInfo userInfo = new UserInfo(
                activeUser,
                userInfoRequest.getFirstName(),
                userInfoRequest.getLastName(),
                userInfoRequest.getAddress(),
                userInfoRequest.getEmail(),
                userInfoRequest.getBirthDate(),
                userInfoRequest.getGender()
        );

        return userService.setUserInfo(userInfo);
    }

    @GetMapping("/userinfo")
    @Secured({Role.Values.ROLE_OWNER, Role.Values.ROLE_USER})
    public UserInfo getUserInfo() {
        String activeUser = SecurityUtil.getActiveUser();
        return userService.getUserInfo(activeUser);
    }
}
