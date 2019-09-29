package bme.cateringunitmonitor;

import bme.cateringunitmonitor.security.UserAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Deprecated
public class SecureHttpInvokerServiceExporter extends HttpInvokerServiceExporter {

    private static final Logger logger = LoggerFactory.getLogger(SecureHttpInvokerServiceExporter.class);
    public static final String REMOTE_AUTH_KEY = "REMOTE_AUTH_KEY";

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            RemoteInvocation remoteInvocation = readRemoteInvocation(request);

            Object authFromInvocation = remoteInvocation.getAttribute(REMOTE_AUTH_KEY);
            if (authFromInvocation == null) {
                logger.debug("No remote authentication in the invocation");
            } else {
                if (authFromInvocation instanceof AnonymousAuthenticationToken) {
                    SecurityContextHolder.getContext()
                            .setAuthentication((AnonymousAuthenticationToken) authFromInvocation);
                } else if (authFromInvocation instanceof UserAuthentication) {
                    SecurityContextHolder.getContext().setAuthentication((UserAuthentication) authFromInvocation);
                } else {
                    logger.warn("Unknown Authentication type in remote invocation! For type: {}",
                            authFromInvocation.getClass());
                }
            }

            writeRemoteInvocationResult(request, response, invokeAndCreateResult(remoteInvocation, getProxy()));
        } catch (ClassNotFoundException ex) {
            throw new ServletException("Class not found during read remote invocation", ex);
        } finally {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
    }
}
