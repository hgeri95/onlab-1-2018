package bme.cateringunitmonitor.apigateway.configuration;

import bme.cateringunitmonitor.remoting.SecureRemoteInocationFactory;
import bme.cateringunitmonitor.remoting.service.IAuthService;
import bme.cateringunitmonitor.remoting.service.ICateringUnitService;
import bme.cateringunitmonitor.remoting.service.IUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

@Configuration
public class RemotingClientConfiguration {

    @Value("${authServiceUrl}")
    private String authServiceUrl;

    @Value("${userServiceUrl}")
    private String userServiceUrl;

    @Value("${cateringServiceUrl}")
    private String cateringServiceUrl;

    @Bean
    public SecureRemoteInocationFactory secureRemoteInocationFactory() {
        return new SecureRemoteInocationFactory();
    }

    @Bean
    public HttpInvokerProxyFactoryBean authInvoker() {
        HttpInvokerProxyFactoryBean invoker = new HttpInvokerProxyFactoryBean();
        invoker.setServiceInterface(IAuthService.class);
        invoker.setServiceUrl(authServiceUrl + IAuthService.REMOTE_ENDPOINT);
        invoker.setRemoteInvocationFactory(secureRemoteInocationFactory());
        return invoker;
    }

    @Bean
    public HttpInvokerProxyFactoryBean userInvoker() {
        HttpInvokerProxyFactoryBean invoker = new HttpInvokerProxyFactoryBean();
        invoker.setServiceInterface(IUserService.class);
        invoker.setServiceUrl(userServiceUrl + IUserService.REMOTE_ENDPOINT);
        invoker.setRemoteInvocationFactory(secureRemoteInocationFactory());
        return invoker;
    }

    @Bean
    public HttpInvokerProxyFactoryBean cateringUnitInvoker() {
        HttpInvokerProxyFactoryBean invoker = new HttpInvokerProxyFactoryBean();
        invoker.setServiceInterface(ICateringUnitService.class);
        invoker.setServiceUrl(cateringServiceUrl + ICateringUnitService.REMOTE_ENDPOINT);
        invoker.setRemoteInvocationFactory(secureRemoteInocationFactory());
        return invoker;
    }
}
