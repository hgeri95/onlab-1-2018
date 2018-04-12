package bme.cateringunitmonitor.userservice.service;

import bme.cateringunitmonitor.entities.user.api.LoginResponse;
import bme.cateringunitmonitor.entities.user.entity.User;
import bme.cateringunitmonitor.entities.user.wrapper.RefreshToken;
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

    public LoginResponse generateAndStoreTokens(User user) {
        logger.debug("Generate token for user: {}", user.toString());
        LoginResponse response = tokenGeneratorService.createTokens(user);
        tokenStore.storeRefreshToken(user.getUsername(),
                response.getRefreshToken(), response.getRefreshTokenExpireDate());
        return response;
    }

    public void invalidateToken(String username) {
        tokenStore.deleteRefreshToken(username);
    }

    public RefreshToken getRefreshToken(String username) {
        return tokenStore.getRefreshToken(username);
    }
}
