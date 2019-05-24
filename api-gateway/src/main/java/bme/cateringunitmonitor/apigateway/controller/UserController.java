package bme.cateringunitmonitor.apigateway.controller;

import bme.cateringunitmonitor.api.Role;
import bme.cateringunitmonitor.api.dto.UserDTO;
import bme.cateringunitmonitor.api.dto.UserInfoDTO;
import bme.cateringunitmonitor.api.remoting.controller.IUserController;
import bme.cateringunitmonitor.utils.feign.CustomErrorDecoder;
import bme.cateringunitmonitor.utils.feign.FeignSecurityInterceptor;
import feign.Contract;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private IUserController userController;

    @PostMapping("/sign-up")
    public UserDTO signUp(@Valid @RequestBody UserDTO user) {
        return userController.signUp(user);
    }

    @GetMapping("/get-available-roles")
    public List<String> getAvailableRoles() {
        return userController.getAvailableRoles();
    }

    @PostMapping("/userinfo")
    @Secured({Role.Values.ROLE_OWNER, Role.Values.ROLE_USER, Role.Values.ROLE_ADMIN})
    public UserInfoDTO setUserInfo(@Valid @RequestBody UserInfoDTO userInfoRequest) {
        return userController.setUserInfo(userInfoRequest);
    }

    @GetMapping("/userinfo")
    @Secured({Role.Values.ROLE_OWNER, Role.Values.ROLE_USER, Role.Values.ROLE_ADMIN})
    public UserInfoDTO getUserInfo() {
        return userController.getUserInfo();
    }

    @PutMapping("/userinfo")
    @Secured({Role.Values.ROLE_OWNER, Role.Values.ROLE_USER, Role.Values.ROLE_ADMIN})
    public UserInfoDTO updateUserInfo(@Valid @RequestBody UserInfoDTO userInfoRequest) {
        return userController.updateUserInfo(userInfoRequest);
    }
}
