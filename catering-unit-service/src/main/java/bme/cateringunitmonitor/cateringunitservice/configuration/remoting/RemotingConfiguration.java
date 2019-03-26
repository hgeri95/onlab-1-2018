package bme.cateringunitmonitor.cateringunitservice.configuration.remoting;

import bme.cateringunitmonitor.SecureHttpInvokerServiceExporter;
import bme.cateringunitmonitor.api.remoting.service.ICateringUnitService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class RemotingConfiguration {

    @Bean(ICateringUnitService.REMOTE_ENDPOINT)
    public SecureHttpInvokerServiceExporter cateringServiceExporter(ICateringUnitService cateringUnitService) {
        SecureHttpInvokerServiceExporter exporter = new SecureHttpInvokerServiceExporter();
        exporter.setServiceInterface(ICateringUnitService.class);
        exporter.setService(cateringUnitService);

        return exporter;
    }
}
