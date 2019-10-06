package bme.cateringunitmonitor.api.remoting.controller;

import bme.cateringunitmonitor.api.dto.AuthRefreshRequest;
import bme.cateringunitmonitor.api.dto.LoginRequest;
import bme.cateringunitmonitor.api.dto.LoginResponse;
import bme.cateringunitmonitor.feign.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "AuthController", url = "${authServiceUrl}", configuration = FeignConfiguration.class)
public interface IAuthController {

    String BASE_PATH = "api/v1/authenticate";

    @PostMapping(BASE_PATH + "/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest);

    @PostMapping(BASE_PATH + "/logout")
    public ResponseEntity logout();

    @PostMapping(BASE_PATH + "/refresh")
    public ResponseEntity<LoginResponse> refresh(@RequestBody @Valid AuthRefreshRequest authRefreshRequest);
}
