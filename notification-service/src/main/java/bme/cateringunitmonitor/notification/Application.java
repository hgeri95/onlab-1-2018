package bme.cateringunitmonitor.notification;


import bme.cateringunitmonitor.api.remoting.controller.IUserController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.core.context.SecurityContextHolder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = "bme.cateringunitmonitor", exclude = {SecurityAutoConfiguration.class})
@Slf4j
@EnableSwagger2
@EnableFeignClients(clients = IUserController.class)
@ServletComponentScan//To scan @WebFilter
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("Notification Service started!");
        //SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);//Hold SecurityContext in async calls!
    }
}
