package bme.cateringunitmonitor.apigateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "bme.cateringunitmonitor")
@Slf4j
@EnableFeignClients
public class Application {

    public  static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("API Gateway started!");
    }
}
