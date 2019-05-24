package bme.cateringunitmonitor.api.remoting.controller;

import bme.cateringunitmonitor.api.dto.AuthRefreshRequest;
import bme.cateringunitmonitor.api.dto.LoginRequest;
import bme.cateringunitmonitor.api.dto.LoginResponse;
import bme.cateringunitmonitor.utils.feign.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "AuthController", url = "${authServiceUrl}", configuration = FeignConfiguration.class)
public interface IAuthController {

    @PostMapping(value = "/authenticate/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest);

    @PostMapping("/authenticate/logout")
    public ResponseEntity logout();

    @PostMapping("/authenticate/refresh")
    public ResponseEntity<LoginResponse> refresh(@RequestBody AuthRefreshRequest authRefreshRequest);
}
