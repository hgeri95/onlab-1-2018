package bme.cateringunitmonitor.userservice.security;

import io.jsonwebtoken.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader(SecurityConstants.HEADER_STRING);

        if (header != null && header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            final String token = header.substring(SecurityConstants.TOKEN_PREFIX.length());

            UsernamePasswordAuthenticationToken authentication = getAuthentication(token);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } else {
            chain.doFilter(request, response);
            SecurityContextHolder.getContext().setAuthentication(null);
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        if (token != null) {
            try {
                final Claims claims = Jwts.parser()
                        .setSigningKey(SecurityConstants.SECRET)
                        .parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
                        .getBody();
                String username = claims.getSubject();
                //TODO extend with expDate, rigths...
                if (username != null) {
                    return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
                } else {
                    return null;
                }
            } catch (SignatureException ex) {
                logger.error("Invalid JWT signature");
            } catch (MalformedJwtException ex) {
                logger.error("Invalid JWT token");
            } catch (ExpiredJwtException ex) {
                logger.error("Expired JWT token");
            } catch (UnsupportedJwtException ex) {
                logger.error("Unsupported JWT token");
            } catch (IllegalArgumentException ex) {
                logger.error("JWT claims string is empty.");
            }
            return null;
        } else {
            return null;
        }
    }
}
