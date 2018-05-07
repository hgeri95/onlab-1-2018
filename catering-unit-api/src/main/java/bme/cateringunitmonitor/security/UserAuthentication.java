package bme.cateringunitmonitor.security;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserAuthentication implements Authentication {

    private UserClaims userClaims;
    private boolean authenticated = true;

    public UserAuthentication(Claims claims) {
        this.userClaims = new UserClaims(claims);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        userClaims.getRoles().forEach(role ->
                grantedAuthorities.add(new SimpleGrantedAuthority(role)));

        return grantedAuthorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return userClaims;
    }

    @Override
    public Object getPrincipal() {
        return userClaims.getUsername();
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        this.authenticated = b;
    }

    @Override
    public String getName() {
        return userClaims.getUsername();
    }

    public UserClaims getUserClaims() {
        return userClaims;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UserAuthentication{");
        sb.append("userClaims=").append(userClaims);
        sb.append(", authenticated=").append(authenticated);
        sb.append('}');
        return sb.toString();
    }
}
