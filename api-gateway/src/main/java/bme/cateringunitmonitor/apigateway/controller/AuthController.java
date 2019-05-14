package bme.cateringunitmonitor.apigateway.controller;

import bme.cateringunitmonitor.api.dto.AuthRefreshRequest;
import bme.cateringunitmonitor.api.dto.LoginRequest;
import bme.cateringunitmonitor.api.dto.LoginResponse;
import bme.cateringunitmonitor.api.remoting.controller.IAuthController;
import bme.cateringunitmonitor.utils.feign.CustomErrorDecoder;
import bme.cateringunitmonitor.utils.feign.FeignSecurityInterceptor;
import feign.Contract;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/authenticate")
@Import(FeignClientsConfiguration.class)
public class AuthController {

    private final IAuthController authController;

    @Autowired
    public AuthController(@Value("${authServiceUrl}") String url, Decoder decoder, Encoder encoder, Contract contract,
                          CustomErrorDecoder errorDecoder, FeignSecurityInterceptor securityInterceptor) {
        this.authController = Feign.builder()
                .encoder(encoder)
                .decoder(decoder)
                .contract(contract)
                .errorDecoder(errorDecoder)
                .requestInterceptor(securityInterceptor)
                .target(IAuthController.class, url);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return authController.login(loginRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity logout() {
        return authController.logout();
    }

    @PostMapping("refresh")
    public ResponseEntity refresh(@RequestBody AuthRefreshRequest authRefreshRequest) {
        return authController.refresh(authRefreshRequest);
    }
}
