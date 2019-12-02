package bme.cateringunitmonitor.userservice.service;

import bme.cateringunitmonitor.api.dto.AuthRefreshRequest;
import bme.cateringunitmonitor.api.dto.LoginRequest;
import bme.cateringunitmonitor.api.dto.LoginResponse;
import bme.cateringunitmonitor.api.dto.UserDTO;
import bme.cateringunitmonitor.api.exception.AuthServiceException;
import bme.cateringunitmonitor.api.wrapper.RefreshToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private static Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    public LoginResponse authenticate(LoginRequest loginRequest) {
        logger.debug("Authenticating user: {}", loginRequest.getUsername());

        UserDTO user = userService.login(new UserDTO(loginRequest.getUsername(), loginRequest.getPassword()));
        return tokenService.generateAndStoreTokens(user);
    }

    public void logout(String username) throws AuthServiceException {
        logger.debug("Logging out user: {}", username);
        UserDTO userToLogout = userService.findUser(username);
        if (userToLogout == null) {
            throw new AuthServiceException("User does not exists!");
        }
        tokenService.invalidateToken(userToLogout.getUsername());
    }

    public LoginResponse refresh(AuthRefreshRequest refreshRequest) throws AuthServiceException {
        logger.debug("Refresh token for user: {}", refreshRequest.getUserName());

        RefreshToken storedRefreshToken = tokenService.getRefreshToken(refreshRequest.getUserName());
        String refreshToken = refreshRequest.getRefreshToken();
        LocalDateTime now = LocalDateTime.now();

        if (storedRefreshToken == null || storedRefreshToken.getRefreshTokenExpireDate().isBefore(now)) {
            logger.debug("No valid refresh token for user: {}", refreshRequest.getUserName());
            throw new AuthServiceException("Invalid token!");
        } else {
            if (refreshToken.equals(storedRefreshToken.getRefreshToken())) {
                UserDTO user = userService.findUser(refreshRequest.getUserName());
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
