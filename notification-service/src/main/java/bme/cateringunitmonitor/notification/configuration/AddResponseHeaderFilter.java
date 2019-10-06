package bme.cateringunitmonitor.notification.configuration;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/v2/api-docs")//Swagger doc access path
@Slf4j
public class AddResponseHeaderFilter implements Filter {
    private static final String ALLOW_ORIGIN_HEADER = "Access-Control-Allow-Origin";
    private FilterConfig config = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig;
        config.getServletContext().log("Initializing Header interceptor");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.debug("Add allow origin header to response: {}", servletResponse);
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader(ALLOW_ORIGIN_HEADER, "*");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        config.getServletContext().log("Destroying Header interceptor");
    }
}
