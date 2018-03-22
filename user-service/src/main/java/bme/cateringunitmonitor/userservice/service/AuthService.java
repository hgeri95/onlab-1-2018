package bme.cateringunitmonitor.userservice.service;

import bme.cateringunitmonitor.entities.user.api.AuthRefreshRequest;
import bme.cateringunitmonitor.entities.user.api.LoginRequest;
import bme.cateringunitmonitor.entities.user.api.LoginResponse;
import bme.cateringunitmonitor.entities.user.entity.User;
import bme.cateringunitmonitor.entities.user.wrapper.RefreshToken;
import bme.cateringunitmonitor.userservice.exception.AuthServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {

    private static Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    public LoginResponse authenticate(LoginRequest loginRequest) {
        logger.debug("Authenticating for user: {}", loginRequest.getUsername());

        User user = userService.login(new User(loginRequest.getUsername(), loginRequest.getPassword()));
        return tokenService.generateAndStoreTokens(user);
    }

    public void logout(String username) {
        logger.debug("Logging out user: {}", username);
        tokenService.invalidateToken(username);
    }

    public LoginResponse refresh(AuthRefreshRequest refreshRequest) throws AuthServiceException {
        logger.debug("Refresh token for user: {}", refreshRequest.getUsername());

        Date now = new Date();
        RefreshToken storedRefreshToken = tokenService.getRefreshToken(refreshRequest.getUsername());
        String refreshToken = refreshRequest.getRefreshToken();

        if (storedRefreshToken == null || storedRefreshToken.getRefreshTokenExpireDate().before(now)) {
            logger.debug("No valid refresh token for user: {}", refreshRequest.getUsername());
            throw new AuthServiceException("Invalid token!");
        } else {
            if (refreshToken.equals(storedRefreshToken.getRefreshToken())) {
                User user = userService.findUser(refreshRequest.getUsername());
                if (user != null) {
                    return tokenService.generateAndStoreTokens(user);
                } else {
                    throw new AuthServiceException("User not found!");
                }
            } else {
                throw new AuthServiceException("Bad or invalid token!");
            }
        }
    }
}
