package bme.cateringunitmonitor.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private static Logger logger = LoggerFactory.getLogger(JWTAuthorizationFilter.class);

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader(SecurityConstants.HEADER_STRING);
        logger.debug("Authorization header in request: {}", header);

        if (header != null && header.startsWith(SecurityConstants.TOKEN_PREFIX) && !header.contains("undefined")) {
            final String token = header.substring(SecurityConstants.TOKEN_PREFIX.length());
            logger.debug("Token in header: {}", token);
            UserAuthentication authentication = getAuthentication(token, response);

            if (authentication != null) {
                logger.debug("Set security context: {}", authentication.toString());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                chain.doFilter(request, response);
                SecurityContextHolder.getContext().setAuthentication(null);
            }
        } else {
            chain.doFilter(request, response);
            SecurityContextHolder.getContext().setAuthentication(null);
        }
    }

    private UserAuthentication getAuthentication(String token, HttpServletResponse response) throws IOException {
        if (token != null) {
            try {
                final Claims claims = Jwts.parser()
                        .setSigningKey(SecurityConstants.SECRET)
                        .parseClaimsJws(token)
                        .getBody();

                return new UserAuthentication(claims);
            } catch (MalformedJwtException ex) {
                logger.error("Invalid JWT token");
                handleError(response, ex.getMessage());
            } catch (ExpiredJwtException ex) {
                logger.error("Expired JWT token");
                handleError(response, ex.getMessage());
            } catch (UnsupportedJwtException ex) {
                logger.error("Unsupported JWT token");
                handleError(response, ex.getMessage());
            } catch (IllegalArgumentException ex) {
                logger.error("JWT claims string is empty.");
                handleError(response, ex.getMessage());
            }
            return null;
        } else {
            return null;
        }
    }

    private void handleError(HttpServletResponse response, String errorMessage) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, errorMessage);
    }
}
