package bme.cateringunitmonitor.userservice.service;

import bme.cateringunitmonitor.api.wrapper.RefreshToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenStore {

    private static Logger logger = LoggerFactory.getLogger(TokenStore.class);
    private Map<Long, RefreshToken> tokens = new HashMap<>();

    public void storeRefreshToken(Long id, String refreshToken, LocalDateTime expireDate) {
        logger.debug("Refresh token to store for user: {}", id);
        tokens.put(id, new RefreshToken(refreshToken, expireDate));
    }

    public void deleteRefreshToken(Long userId) {
        logger.debug("Delete token for user with id: {}", userId);
        tokens.remove(userId);
    }

    public RefreshToken getRefreshToken(Long userId) {
        return tokens.get(userId);
    }
}
