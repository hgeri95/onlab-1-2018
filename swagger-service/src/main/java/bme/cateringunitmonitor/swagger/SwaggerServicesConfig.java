package bme.cateringunitmonitor.swagger;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Primary
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "documentation.swagger")
@Getter
@Setter
public class SwaggerServicesConfig {
    List<SwaggerService> services;
}
