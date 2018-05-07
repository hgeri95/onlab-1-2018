package bme.cateringunitmonitor.userservice.configuration.remoting;

import bme.cateringunitmonitor.remoting.SecureHttpInvokerServiceExporter;
import bme.cateringunitmonitor.remoting.service.IAuthService;
import bme.cateringunitmonitor.remoting.service.IUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class RemotingConfiguration {

    @Bean(IAuthService.REMOTE_ENDPOINT)
    public SecureHttpInvokerServiceExporter authServiceExporter(IAuthService authService) {
        SecureHttpInvokerServiceExporter exporter = new SecureHttpInvokerServiceExporter();
        exporter.setServiceInterface(IAuthService.class);
        exporter.setService(authService);

        return exporter;
    }

    @Bean(IUserService.REMOTE_ENDPOINT)
    public SecureHttpInvokerServiceExporter userServiceExporter(IUserService userService) {
        SecureHttpInvokerServiceExporter exporter = new SecureHttpInvokerServiceExporter();
        exporter.setServiceInterface(IUserService.class);
        exporter.setService(userService);

        return exporter;
    }
}
