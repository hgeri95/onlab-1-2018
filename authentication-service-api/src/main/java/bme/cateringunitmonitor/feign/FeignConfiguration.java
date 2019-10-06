package bme.cateringunitmonitor.feign;

import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

@Slf4j
public class FeignConfiguration {

    public FeignConfiguration() {
        log.info("Load Feign configuration...");
    }

    @Bean
    public CustomErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new FeignSecurityInterceptor();
    }
}
