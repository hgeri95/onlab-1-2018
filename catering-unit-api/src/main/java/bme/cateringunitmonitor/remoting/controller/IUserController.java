package bme.cateringunitmonitor.remoting.controller;

import bme.cateringunitmonitor.entities.user.api.UserInfoRequest;
import bme.cateringunitmonitor.entities.user.entity.Role;
import bme.cateringunitmonitor.entities.user.entity.User;
import bme.cateringunitmonitor.entities.user.entity.UserInfo;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IUserController {

    @PostMapping("/sign-up")
    public User signUp(@RequestBody User user);

    @GetMapping("/get-available-roles")
    public List<String> getAvailableRoles();

    @PostMapping("/userinfo")
    @Secured({Role.Values.ROLE_OWNER, Role.Values.ROLE_USER})
    public UserInfo setUserInfo(@RequestBody UserInfoRequest userInfoRequest);

    @GetMapping("/userinfo")
    @Secured({Role.Values.ROLE_OWNER, Role.Values.ROLE_USER})
    public UserInfo getUserInfo();
}
