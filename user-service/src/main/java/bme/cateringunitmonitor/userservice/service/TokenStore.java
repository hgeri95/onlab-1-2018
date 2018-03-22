package bme.cateringunitmonitor.userservice.service;

import bme.cateringunitmonitor.entities.user.wrapper.RefreshToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenStore {

    private static Logger logger = LoggerFactory.getLogger(TokenStore.class);
    private Map<String, RefreshToken> tokens = new HashMap<>();

    public void storeRefreshToken(String id, String refreshToken, Date expireDate) {
        logger.debug("Refresh token to store for user: {}", id);
        tokens.put(id, new RefreshToken(refreshToken, expireDate));
    }

    public void deleteRefreshToken(String id) {
        logger.debug("Delete token for user: {}", id);
        tokens.remove(id);
    }

    public RefreshToken getRefreshToken(String id) {
        return tokens.get(id);
    }


}
