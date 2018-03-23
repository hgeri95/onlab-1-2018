package bme.cateringunitmonitor.userservice.service;

import bme.cateringunitmonitor.entities.user.api.LoginResponse;
import bme.cateringunitmonitor.entities.user.entity.User;
import bme.cateringunitmonitor.userservice.security.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TokenGeneratorService {

    @Value("${jwt.accessTokenValidity.minutes:1}")
    private int accessTokenValidity;

    @Value("${jwt.refreshTokenLength:16}")
    private int refreshTokenLength;

    @Value("${jwt.refreshTokenValidity.minutes}")
    private int refreshTokenValidity;

    public LoginResponse createTokens(User user) {
        List<String> roles = new ArrayList<>();
        user.getRoles().forEach(role -> roles.add(role.getRoleName()));

        Date now = new Date();
        Date tokenExpireDate = DateUtils.addMinutes(now, accessTokenValidity);

        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(tokenExpireDate)
                .claim(SecurityConstants.ROLES_KEY, roles)
                .claim(SecurityConstants.USERNAME_KEY, user.getUsername())
                .signWith(SignatureAlgorithm.HS256, SecurityConstants.SECRET).compact();

        String refreshToken = generateRandomSecureString(refreshTokenLength);
        Date refreshTokenExpireDate = DateUtils.addMinutes(now, refreshTokenValidity);

        return new LoginResponse(token, tokenExpireDate,
                refreshToken, refreshTokenExpireDate,
                user);
    }

    private String generateRandomSecureString(int length) {
        SecureRandom secureRandom = new SecureRandom();
        if (length < 1) {
            throw new IllegalArgumentException("length < 1");
        }

        char[] buffer = new char[length];
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = SecurityConstants.SYMBOLS.charAt(
                    secureRandom.nextInt(SecurityConstants.SYMBOLS.length()));
        }

        return new String(buffer);
    }
}
