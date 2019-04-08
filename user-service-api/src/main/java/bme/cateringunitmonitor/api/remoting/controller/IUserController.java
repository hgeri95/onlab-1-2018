package bme.cateringunitmonitor.api.remoting.controller;

import bme.cateringunitmonitor.api.Role;
import bme.cateringunitmonitor.api.dao.UserDAO;
import bme.cateringunitmonitor.api.dao.UserInfoDAO;
import bme.cateringunitmonitor.api.dto.UserInfoRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IUserController {

    @PostMapping("/sign-up")
    public UserDAO signUp(@RequestBody UserDAO user);

    @GetMapping("/get-available-roles")
    public List<String> getAvailableRoles();

    @PostMapping("/userinfo")
    @Secured({Role.Values.ROLE_OWNER, Role.Values.ROLE_USER})
    public UserInfoDAO setUserInfo(@RequestBody UserInfoRequest userInfoRequest);

    @GetMapping("/userinfo")
    @Secured({Role.Values.ROLE_OWNER, Role.Values.ROLE_USER})
    public UserInfoDAO getUserInfo();
}
