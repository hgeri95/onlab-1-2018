package bme.cateringunitmonitor.remoting.controller;

import bme.cateringunitmonitor.entities.user.api.AuthRefreshRequest;
import bme.cateringunitmonitor.entities.user.api.LoginRequest;
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
