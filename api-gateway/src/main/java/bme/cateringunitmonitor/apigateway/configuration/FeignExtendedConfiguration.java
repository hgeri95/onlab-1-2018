package bme.cateringunitmonitor.apigateway.configuration;

import bme.cateringunitmonitor.utils.feign.CustomErrorDecoder;
import bme.cateringunitmonitor.utils.feign.FeignSecurityInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignExtendedConfiguration {
    @Bean
    public CustomErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }

    @Bean
    public FeignSecurityInterceptor requestInterceptor() {
        return new FeignSecurityInterceptor();
    }
}
