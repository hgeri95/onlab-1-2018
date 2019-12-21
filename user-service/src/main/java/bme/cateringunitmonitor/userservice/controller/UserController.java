package bme.cateringunitmonitor.userservice.controller;

import bme.cateringunitmonitor.api.Role;
import bme.cateringunitmonitor.api.dto.*;
import bme.cateringunitmonitor.api.exception.BadRequestException;
import bme.cateringunitmonitor.api.exception.UserServiceException;
import bme.cateringunitmonitor.api.remoting.controller.IUserController;
import bme.cateringunitmonitor.security.SecurityUtil;
import bme.cateringunitmonitor.userservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
public class UserController implements IUserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Override
    public UserDTO signUp(@Valid UserRequest user) throws BadRequestException {
        logger.debug("Sign up new user: {}", user);
        try {
            return userService.create(user);
        } catch (IllegalArgumentException | UserServiceException ex) {
            logger.warn("Failed to register new user: {}", ex);
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public int deleteUser() {
        String activeUser = SecurityUtil.getActiveUser();
        logger.debug("Delete user: {}", activeUser);
        try {
            return userService.delete(activeUser);
        } catch (UserServiceException ex) {
            logger.warn("Failed to delete user: {}", activeUser);
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public int deleteUserByAdmin(String username) {
        logger.info("User to delete: {} by admin", username);
        try {
            return userService.delete(username);
        } catch (UserServiceException ex) {
            logger.warn("Failed to delete user: {} by admin", username);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @Override
    public List<String> getAvailableRoles() {
        return Role.getAllRoles();
    }

    @Override
    public UserInfoDTO setUserInfo(@Valid UserInfoRequest userInfoRequest) {
        String activeUser = SecurityUtil.getActiveUser();
        logger.debug("Set user info: {}, for user: {}", userInfoRequest, activeUser);
        userInfoRequest.setUsername(activeUser);
        try {
            return userService.saveUserInfo(userInfoRequest);
        } catch (UserServiceException ex) {
            logger.warn("User info is already exists for user: {}, exception: {}", userInfoRequest.getUsername(), ex);
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public UserInfoDTO getUserInfo() throws BadRequestException {
        String activeUser = SecurityUtil.getActiveUser();
        try {
            return userService.getUserInfo(activeUser);
        } catch (UserServiceException ex) {
            logger.warn("Userinfo not found: {}", ex);
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public UserInfoDTO updateUserInfo(@Valid UserInfoRequest userInfoRequest) {
        String username = SecurityUtil.getActiveUser();
        userInfoRequest.setUsername(username);
        return userService.updateUserInfo(userInfoRequest);
    }

    @Override
    public UserInfoDTO getUserInfoByUsername(String username) {
        try {
            return userService.getUserInfo(username);
        } catch (UserServiceException ex) {
            logger.warn("User info not found: {}", ex);
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public List<UserInfoDTO> getUserInfosByUsernames(String[] userNames) {
        List<String> userNameCollection = Arrays.asList(userNames);
        return userService.getUserInfos(userNameCollection);
    }

    @Override
    public Boolean checkUserExists(String username) {
        return userService.isUserExists(username);
    }
}
