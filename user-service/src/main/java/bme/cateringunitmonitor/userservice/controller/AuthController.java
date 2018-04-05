package bme.cateringunitmonitor.userservice.controller;


import bme.cateringunitmonitor.entities.user.api.AuthRefreshRequest;
import bme.cateringunitmonitor.entities.user.api.LoginRequest;
import bme.cateringunitmonitor.entities.user.api.UserAuthentication;
import bme.cateringunitmonitor.userservice.exception.AuthServiceException;
import bme.cateringunitmonitor.userservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authenticate")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) {
        try {
            return ResponseEntity.ok(authService.authenticate(loginRequest));
        } catch (AuthServiceException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity logout() {
        authService.logout(getActiveUser());
        return ResponseEntity.ok().build();
    }

    @PostMapping("refresh")
    public ResponseEntity refresh(@RequestBody AuthRefreshRequest authRefreshRequest) {
        try {
            return ResponseEntity.ok(authService.refresh(authRefreshRequest));
        } catch (AuthServiceException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Deprecated
    private static String getUser() {
        UserAuthentication userAuthentication = (UserAuthentication) SecurityContextHolder.getContext()
                .getAuthentication();

        return null; //TODO
    }

    private static String getActiveUser() {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().
                        getAuthentication();

        return authenticationToken.getName();
    }
}
