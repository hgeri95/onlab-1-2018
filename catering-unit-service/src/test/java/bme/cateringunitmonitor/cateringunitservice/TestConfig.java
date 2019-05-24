package bme.cateringunitmonitor.cateringunitservice;

import bme.cateringunitmonitor.cateringunitservice.service.CateringUnitService;
import bme.cateringunitmonitor.cateringunitservice.util.CateringUnitConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Bean
    public CateringUnitService cateringUnitService() {
        return new CateringUnitService();
    }

    @Bean
    public CateringUnitConverter cateringUnitConverter() {
        return new CateringUnitConverter();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
