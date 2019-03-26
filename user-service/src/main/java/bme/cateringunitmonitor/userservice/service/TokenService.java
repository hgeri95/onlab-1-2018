package bme.cateringunitmonitor.userservice.service;

import bme.cateringunitmonitor.api.dao.User;
import bme.cateringunitmonitor.api.dto.LoginResponse;
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

    public LoginResponse generateAndStoreTokens(User user) {
        logger.debug("Generate token for user: {}", user.toString());
        LoginResponse response = tokenGeneratorService.createTokens(user);
        tokenStore.storeRefreshToken(user.getId(),
                response.getRefreshToken(), response.getRefreshTokenExpireDate());
        return response;
    }

    public void invalidateToken(Long userId) {
        tokenStore.deleteRefreshToken(userId);
    }

    public RefreshToken getRefreshToken(Long userId) {
        return tokenStore.getRefreshToken(userId);
    }
}
