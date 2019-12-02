package bme.cateringunitmonitor.userservice.service;

import bme.cateringunitmonitor.api.wrapper.RefreshToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenStore {

    private static Logger logger = LoggerFactory.getLogger(TokenStore.class);
    private Map<String, RefreshToken> tokens = new ConcurrentHashMap<>();

    public void storeRefreshToken(String userName, String refreshToken, LocalDateTime expireDate) {
        logger.debug("Refresh token to store for user: {}", userName);
        tokens.put(userName, new RefreshToken(refreshToken, expireDate));
    }

    public void deleteRefreshToken(String userName) {
        logger.debug("Delete token for user: {}", userName);
        tokens.remove(userName);
    }

    public RefreshToken getRefreshToken(String userName) {
        return tokens.get(userName);
    }
}
