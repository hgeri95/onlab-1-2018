package bme.cateringunitmonitor.userservice.service;

import bme.cateringunitmonitor.api.Role;
import bme.cateringunitmonitor.api.dto.LoginResponse;
import bme.cateringunitmonitor.api.dto.UserDTO;
import bme.cateringunitmonitor.utils.security.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Service
public class TokenGeneratorService {

    @Value("${jwt.accessTokenValidity.minutes:5}")
    private int accessTokenValidity;

    @Value("${jwt.refreshTokenLength:16}")
    private int refreshTokenLength;

    @Value("${jwt.refreshTokenValidity.minutes:10}")
    private int refreshTokenValidity;

    public LoginResponse createTokens(UserDTO user) {
        List<Role> roles = user.getRoles();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tokenExpireDate = now.plusMinutes(accessTokenValidity);
        ZonedDateTime zdt = tokenExpireDate.atZone(ZoneId.systemDefault());
        Date tokenExpireDateToSet = Date.from(zdt.toInstant());

        String token = Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .setExpiration(tokenExpireDateToSet)
                .claim(SecurityConstants.ROLES_KEY, roles)
                .claim(SecurityConstants.USERNAME_KEY, user.getUsername())
                .signWith(SignatureAlgorithm.HS256, SecurityConstants.SECRET).compact();

        String refreshToken = generateRandomSecureString(refreshTokenLength);
        LocalDateTime refreshTokenExpireDate = now.plusMinutes(refreshTokenValidity);

        return new LoginResponse(token, tokenExpireDate,
                refreshToken, refreshTokenExpireDate);
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
