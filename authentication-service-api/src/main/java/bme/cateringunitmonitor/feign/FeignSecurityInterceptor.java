package bme.cateringunitmonitor.feign;

import bme.cateringunitmonitor.security.UserAuthentication;
import bme.cateringunitmonitor.utils.security.SecurityConstants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class FeignSecurityInterceptor implements RequestInterceptor {

    public FeignSecurityInterceptor() {
        log.info("Create Feign Security interceptor...");
    }

    /**
     * Get security token from security context and put it as header to the Feign request.
     * Put a header which indicates this is a feign call.
     *
     * @param requestTemplate
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.debug("Feign security context authentication: {}", authentication);
        if (authentication == null) {
            return;
        }
        UserAuthentication userAuthentication = (UserAuthentication) authentication;
        String token = userAuthentication.getToken();
        if (token == null) {
            return;
        }
        log.debug("Feign interceptor put token string: {}", token);
        requestTemplate.header(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
        requestTemplate.header(SecurityConstants.INNER_CALL_HEADER_STRING, SecurityConstants.INNER_CALL_TOKEN);
    }
}
