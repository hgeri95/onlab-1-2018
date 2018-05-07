package bme.cateringunitmonitor.security;

import io.jsonwebtoken.Claims;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UserClaims implements Serializable {

    private long id;
    private String username;
    private Date expiresAt;
    private List<String> roles;

    /**
     * Use to get user information from jwt token.
     *
     * @param claims
     */
    public UserClaims(Claims claims) {
        this.id = Long.parseLong(claims.getSubject());
        this.username = (String) claims.get(SecurityConstants.USERNAME_KEY);
        this.expiresAt = claims.getExpiration();
        this.roles = (List<String>) claims.get(SecurityConstants.ROLES_KEY);
    }
}
