package bme.cateringunitmonitor.swagger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
//@EnableSwagger2
@EnableConfigurationProperties({SwaggerServicesConfig.class, SwaggerService.class})
@Slf4j
public class SwaggerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwaggerApplication.class, args);
        log.info("Swagger Service started!");
    }
}
