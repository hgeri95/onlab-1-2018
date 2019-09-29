package bme.cateringunitmonitor;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationFactory;
import org.springframework.security.core.context.SecurityContextHolder;

@Deprecated
public class SecureRemoteInvocationFactory implements RemoteInvocationFactory {
    @Override
    public RemoteInvocation createRemoteInvocation(MethodInvocation methodInvocation) {
        RemoteInvocation remoteInvocation = new RemoteInvocation(methodInvocation);
        remoteInvocation.addAttribute(SecureHttpInvokerServiceExporter.REMOTE_AUTH_KEY,
                SecurityContextHolder.getContext().getAuthentication());

        return remoteInvocation;
    }
}
