package bme.cateringunitmonitor.api.remoting.controller;

import bme.cateringunitmonitor.api.dto.AuthRefreshRequest;
import bme.cateringunitmonitor.api.dto.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface IAuthController {

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest);
    @PostMapping("/logout")
    public ResponseEntity logout();
    @PostMapping("/refresh")
    public ResponseEntity refresh(@RequestBody AuthRefreshRequest authRefreshRequest);
}
