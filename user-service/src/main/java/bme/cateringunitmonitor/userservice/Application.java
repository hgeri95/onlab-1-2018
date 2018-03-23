package bme.cateringunitmonitor.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "bme.cateringunitmonitor.userservice")
@EntityScan(basePackages = "bme.cateringunitmonitor.entities")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    //TODO add been passwordencoder
}
