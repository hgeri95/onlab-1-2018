package bme.cateringunitmonitor.swagger;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@EnableConfigurationProperties
@ConfigurationProperties(prefix="documentation.swagger.services")
@Getter
@Setter
@ToString
public class SwaggerService {
    private String name;
    private String url;
    private String version;
}
