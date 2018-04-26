package bme.cateringunitmonitor.userservice.controller;


import bme.cateringunitmonitor.entities.user.api.AuthRefreshRequest;
import bme.cateringunitmonitor.entities.user.api.LoginRequest;
import bme.cateringunitmonitor.userservice.exception.AuthServiceException;
import bme.cateringunitmonitor.userservice.security.SecurityUtil;
import bme.cateringunitmonitor.userservice.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authenticate")
public class AuthController {

    private static Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) {
        logger.debug("Login with user: {}", loginRequest.getUsername());
        try {
            return ResponseEntity.ok(authService.authenticate(loginRequest));
        } catch (AuthServiceException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity logout() {
        String activeUser = SecurityUtil.getActiveUser();
        logger.debug("Logout with user: {}", activeUser);
        authService.logout(activeUser);
        return ResponseEntity.ok().build();

    }

    @PostMapping("refresh")
    public ResponseEntity refresh(@RequestBody AuthRefreshRequest authRefreshRequest) {
        logger.debug("Refresh token for user: {}", authRefreshRequest.getUsername());
        try {
            return ResponseEntity.ok(authService.refresh(authRefreshRequest));
        } catch (AuthServiceException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
