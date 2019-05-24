package bme.cateringunitmonitor.api.remoting.controller;

import bme.cateringunitmonitor.api.Role;
import bme.cateringunitmonitor.api.dto.UserDTO;
import bme.cateringunitmonitor.api.dto.UserInfoDTO;
import bme.cateringunitmonitor.utils.feign.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "UserController", url = "${userServiceUrl}", configuration = FeignConfiguration.class)
public interface IUserController {

    @PostMapping("/users/sign-up")
    public UserDTO signUp(@RequestBody UserDTO user);

    @GetMapping("/users/get-available-roles")
    public List<String> getAvailableRoles();

    @PostMapping("/users/userinfo")
    @Secured({Role.Values.ROLE_OWNER, Role.Values.ROLE_USER, Role.Values.ROLE_ADMIN})
    public UserInfoDTO setUserInfo(@RequestBody UserInfoDTO userInfoRequest);

    @GetMapping("/users/userinfo")
    @Secured({Role.Values.ROLE_OWNER, Role.Values.ROLE_USER, Role.Values.ROLE_ADMIN})
    public UserInfoDTO getUserInfo();

    @PutMapping("/users/userinfo")
    @Secured({Role.Values.ROLE_OWNER, Role.Values.ROLE_USER, Role.Values.ROLE_ADMIN})
    public UserInfoDTO updateUserInfo(@RequestBody UserInfoDTO userInfoRequest);
}
