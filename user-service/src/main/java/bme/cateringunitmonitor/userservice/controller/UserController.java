package bme.cateringunitmonitor.userservice.controller;

import bme.cateringunitmonitor.api.Role;
import bme.cateringunitmonitor.api.dto.UserDTO;
import bme.cateringunitmonitor.api.dto.UserInfoDTO;
import bme.cateringunitmonitor.api.remoting.controller.IUserController;
import bme.cateringunitmonitor.api.remoting.service.IUserService;
import bme.cateringunitmonitor.security.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController implements IUserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    @Override
    public UserDTO signUp(UserDTO user) {
        logger.debug("Sign up new user: {}", user);
        return userService.create(user);
    }

    @Override
    public List<String> getAvailableRoles() {
        return Role.getAllRoles();
    }

    @Override
    public UserInfoDTO setUserInfo(UserInfoDTO userInfoRequest) {
        logger.debug("Set user info: {}", userInfoRequest);
        String activeUser = SecurityUtil.getActiveUser();
        UserInfoDTO userInfo = new UserInfoDTO(
                activeUser,
                userInfoRequest.getFullName(),
                userInfoRequest.getCity(),
                userInfoRequest.getEmail(),
                userInfoRequest.getBirthDate(),
                userInfoRequest.getGender()
        );

        return userService.saveUserInfo(userInfo);
    }

    @Override
    public UserInfoDTO getUserInfo() {
        String activeUser = SecurityUtil.getActiveUser();
        return userService.getUserInfo(activeUser);
    }

    @Override
    public UserInfoDTO updateUserInfo(UserInfoDTO userInfoRequest) {
        String activeUser = SecurityUtil.getActiveUser();
        return userService.updateUserInfo(new UserInfoDTO(
                SecurityUtil.getActiveUser(),
                userInfoRequest.getFullName(),
                userInfoRequest.getCity(),
                userInfoRequest.getEmail(),
                userInfoRequest.getBirthDate(),
                userInfoRequest.getGender()
        ));
    }
}
