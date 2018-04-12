package bme.cateringunitmonitor.userservice.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    private static Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

    public static String getActiveUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAuthentication authenticationToken = (UserAuthentication) authentication;

        return authenticationToken.getName();
    }
}
