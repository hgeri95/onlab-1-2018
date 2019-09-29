package bme.cateringunitmonitor.api.remoting.controller;

import bme.cateringunitmonitor.api.Role;
import bme.cateringunitmonitor.api.dto.UserDTO;
import bme.cateringunitmonitor.api.dto.UserInfoDTO;
import bme.cateringunitmonitor.api.dto.UserInfoRequest;
import bme.cateringunitmonitor.api.dto.UserRequest;
import bme.cateringunitmonitor.api.exception.BadRequestException;
import bme.cateringunitmonitor.utils.feign.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@FeignClient(name = "UserController", url = "${userServiceUrl}", configuration = FeignConfiguration.class)
public interface IUserController {

    String BASE_PATH = "api/v1/users";

    @PostMapping(BASE_PATH + "/sign-up")
    public UserDTO signUp(@RequestBody @Valid UserRequest userRequest) throws BadRequestException;

    @DeleteMapping(BASE_PATH + "/delete")
    public int deleteUser();

    @GetMapping(BASE_PATH + "/get-available-roles")
    public List<String> getAvailableRoles();

    @PostMapping(BASE_PATH + "/userinfo")
    @Secured({Role.Values.ROLE_OWNER, Role.Values.ROLE_USER, Role.Values.ROLE_ADMIN})
    public UserInfoDTO setUserInfo(@RequestBody @Valid UserInfoRequest userInfoRequest);

    @GetMapping(BASE_PATH + "/userinfo")
    @Secured({Role.Values.ROLE_OWNER, Role.Values.ROLE_USER, Role.Values.ROLE_ADMIN})
    public UserInfoDTO getUserInfo();

    @PutMapping(BASE_PATH + "/userinfo")
    @Secured({Role.Values.ROLE_OWNER, Role.Values.ROLE_USER, Role.Values.ROLE_ADMIN})
    public UserInfoDTO updateUserInfo(@RequestBody @Valid UserInfoRequest userInfoRequest);

    @GetMapping(BASE_PATH + "/userinfo/{username}")
    @Secured({Role.Values.ROLE_ADMIN})
    public UserInfoDTO getUserInfoByUsername(@PathVariable("username") String username);
}
