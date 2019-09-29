package bme.cateringunitmonitor.cateringunitservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = "bme.cateringunitmonitor", exclude = {SecurityAutoConfiguration.class})
@EntityScan(basePackages = "bme.cateringunitmonitor")
@Slf4j
@EnableSwagger2
@ServletComponentScan//To scan @WebFilter
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("Catering unit service started!");
    }
}
