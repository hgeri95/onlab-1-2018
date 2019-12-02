package bme.cateringunitmonitor.userservice.service;

import bme.cateringunitmonitor.api.dto.LoginResponse;
import bme.cateringunitmonitor.api.dto.UserDTO;
import bme.cateringunitmonitor.api.wrapper.RefreshToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private static Logger logger = LoggerFactory.getLogger(TokenService.class);

    @Autowired
    private TokenGeneratorService tokenGeneratorService;

    @Autowired
    private TokenStore tokenStore;

    public LoginResponse generateAndStoreTokens(UserDTO user) {
        logger.debug("Generate token for user: {}", user.toString());
        LoginResponse response = tokenGeneratorService.createTokens(user);
        tokenStore.storeRefreshToken(user.getUsername(),
                response.getRefreshToken(), response.getRefreshTokenExpireDate().toLocalDateTime());
        logger.debug("Login response: {}", response);
        return response;
    }

    public void invalidateToken(String userName) {
        tokenStore.deleteRefreshToken(userName);
    }

    public RefreshToken getRefreshToken(String userName) {
        return tokenStore.getRefreshToken(userName);
    }
}
