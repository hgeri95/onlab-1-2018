package bme.cateringunitmonitor.security;

import bme.cateringunitmonitor.api.Role;
import io.jsonwebtoken.Claims;
import lombok.ToString;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ToString
public class UserAuthentication implements Authentication {

    private String token;
    private UserClaims userClaims;
    private boolean authenticated = true;
    private boolean isInnerCall;

    public UserAuthentication(Claims claims, String token, boolean isInnerCall) {
        this.userClaims = new UserClaims(claims);
        this.token = token;
        this.isInnerCall = isInnerCall;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        userClaims.getRoles().forEach(role ->
                grantedAuthorities.add(new SimpleGrantedAuthority(role)));

        //Add technical role if it is an inner call. (Used by Feign calls)
        /*if (isInnerCall) {
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.Values.ROLE_TECHNICAL));
        }*/

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

    public String getToken() {
        return token;
    }
}
