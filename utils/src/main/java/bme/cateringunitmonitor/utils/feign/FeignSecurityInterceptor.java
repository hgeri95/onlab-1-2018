package bme.cateringunitmonitor.utils.feign;

import bme.cateringunitmonitor.utils.security.SecurityConstants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class FeignSecurityInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return;
        }
        HttpServletRequest request = requestAttributes.getRequest();
        if (request == null) {
            return;
        }
        String token = request.getHeader(SecurityConstants.HEADER_STRING);
        if (token == null) {
            return;
        }
        log.debug("Interceptor put token string: {}", token);
        requestTemplate.header(SecurityConstants.HEADER_STRING, token);
    }
}
