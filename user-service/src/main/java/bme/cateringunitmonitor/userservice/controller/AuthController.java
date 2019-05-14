package bme.cateringunitmonitor.userservice.controller;


import bme.cateringunitmonitor.api.dto.AuthRefreshRequest;
import bme.cateringunitmonitor.api.dto.LoginRequest;
import bme.cateringunitmonitor.api.dto.LoginResponse;
import bme.cateringunitmonitor.api.exception.AuthServiceException;
import bme.cateringunitmonitor.api.remoting.controller.IAuthController;
import bme.cateringunitmonitor.api.remoting.service.IAuthService;
import bme.cateringunitmonitor.security.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements IAuthController {

    private static Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private IAuthService authService;

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {
        logger.debug("Login with user: {}", loginRequest.getUsername());
        try {
            return ResponseEntity.ok(authService.authenticate(loginRequest));
        } catch (AuthServiceException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Override
    public ResponseEntity logout() {
        String activeUser = SecurityUtil.getActiveUser();
        logger.debug("Logout with user: {}", activeUser);
        authService.logout(activeUser);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<LoginResponse> refresh(AuthRefreshRequest authRefreshRequest) {
        logger.debug("Refresh token for user with id: {}", authRefreshRequest.getUserId());
        try {
            return ResponseEntity.ok(authService.refresh(authRefreshRequest));
        } catch (AuthServiceException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
