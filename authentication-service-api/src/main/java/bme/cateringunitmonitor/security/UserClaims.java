package bme.cateringunitmonitor.security;

import bme.cateringunitmonitor.utils.security.SecurityConstants;
import io.jsonwebtoken.Claims;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@ToString
public class UserClaims implements Serializable {

    private final Long id;
    private final String username;
    private final Date expiresAt;
    private final List<String> roles;

    /**
     * Use to get user information from jwt token.
     *
     * @param claims
     */
    @SuppressWarnings("unchecked")
    public UserClaims(Claims claims) {
        this.id = Long.parseLong(claims.getSubject());
        this.username = (String) claims.get(SecurityConstants.USERNAME_KEY);
        this.expiresAt = claims.getExpiration();
        this.roles = (List<String>) claims.get(SecurityConstants.ROLES_KEY);
    }
}
