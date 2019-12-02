package bme.cateringunitmonitor.userservice.controller;


import bme.cateringunitmonitor.api.dto.AuthRefreshRequest;
import bme.cateringunitmonitor.api.dto.LoginRequest;
import bme.cateringunitmonitor.api.dto.LoginResponse;
import bme.cateringunitmonitor.api.exception.AuthServiceException;
import bme.cateringunitmonitor.api.exception.UnauthorizedException;
import bme.cateringunitmonitor.api.remoting.controller.IAuthController;
import bme.cateringunitmonitor.security.SecurityUtil;
import bme.cateringunitmonitor.userservice.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController implements IAuthController {

    private static Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @Override
    public ResponseEntity<LoginResponse> login(@Valid LoginRequest loginRequest) throws UnauthorizedException {
        logger.debug("Login with user: {}", loginRequest.getUsername());
        return ResponseEntity.ok(authService.authenticate(loginRequest));
    }

    @Override
    public ResponseEntity logout() {
        String activeUser = SecurityUtil.getActiveUser();
        logger.debug("Logout with user: {}", activeUser);
        try {
            authService.logout(activeUser);
        } catch (AuthServiceException ex) {
            logger.warn("Failed to sign out user: {}", activeUser);
            throw new UnauthorizedException(ex.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<LoginResponse> refresh(@Valid AuthRefreshRequest authRefreshRequest) {
        logger.debug("Refresh token for user: {}", authRefreshRequest.getUserName());
        try {
            return ResponseEntity.ok(authService.refresh(authRefreshRequest));
        } catch (AuthServiceException ex) {
            logger.warn("Unsuccessful token refresh: {}", ex);
            throw new UnauthorizedException(ex.getMessage());
        }
    }
}
