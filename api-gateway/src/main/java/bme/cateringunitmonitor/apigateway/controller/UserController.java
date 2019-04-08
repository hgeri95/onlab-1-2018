package bme.cateringunitmonitor.apigateway.controller;

import bme.cateringunitmonitor.api.Role;
import bme.cateringunitmonitor.api.dao.UserDAO;
import bme.cateringunitmonitor.api.dao.UserInfoDAO;
import bme.cateringunitmonitor.api.dto.UserInfoRequest;
import bme.cateringunitmonitor.api.remoting.controller.IUserController;
import bme.cateringunitmonitor.api.remoting.service.IUserService;
import bme.cateringunitmonitor.security.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController implements IUserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    @PostMapping("/sign-up")
    public UserDAO signUp(@RequestBody UserDAO user) {
        logger.debug("Sign up new user: {}", user);
        return userService.create(user);
    }

    @GetMapping("/get-available-roles")
    public List<String> getAvailableRoles() {
        return Role.getAllRoles();
    }

    @PostMapping("/userinfo")
    @Secured({Role.Values.ROLE_OWNER, Role.Values.ROLE_USER, Role.Values.ROLE_ADMIN})
    public UserInfoDAO setUserInfo(@RequestBody UserInfoRequest userInfoRequest) {
        logger.debug("Set user info: {}", userInfoRequest);
        String activeUser = SecurityUtil.getActiveUser();
        UserInfoDAO userInfo = new UserInfoDAO(
                activeUser,
                userInfoRequest.getFullName(),
                userInfoRequest.getCity(),
                userInfoRequest.getEmail(),
                userInfoRequest.getBirthDate(),
                userInfoRequest.getGender()
        );

        return userService.saveUserInfo(userInfo);
    }

    @GetMapping("/userinfo")
    @Secured({Role.Values.ROLE_OWNER, Role.Values.ROLE_USER, Role.Values.ROLE_ADMIN})
    public UserInfoDAO getUserInfo() {
        String activeUser = SecurityUtil.getActiveUser();
        return userService.getUserInfo(activeUser);
    }

    @PutMapping("/userinfo")
    @Secured({Role.Values.ROLE_OWNER, Role.Values.ROLE_USER, Role.Values.ROLE_ADMIN})
    public UserInfoDAO updateUserInfo(@RequestBody UserInfoRequest userInfoRequest) {
        String activeUser = SecurityUtil.getActiveUser();
        return userService.updateUserInfo(new UserInfoDAO(
                SecurityUtil.getActiveUser(),
                userInfoRequest.getFullName(),
                userInfoRequest.getCity(),
                userInfoRequest.getEmail(),
                userInfoRequest.getBirthDate(),
                userInfoRequest.getGender()
        ));
    }
}
