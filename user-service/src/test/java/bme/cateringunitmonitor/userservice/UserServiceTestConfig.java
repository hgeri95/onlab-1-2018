package bme.cateringunitmonitor.userservice;

import bme.cateringunitmonitor.userservice.service.UserService;
import bme.cateringunitmonitor.userservice.util.UserConverter;
import bme.cateringunitmonitor.userservice.util.UserInfoConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserServiceTestConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UserService userService() {
        return new UserService();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public UserInfoConverter userInfoConverter() {
        return new UserInfoConverter();
    }

    @Bean
    public UserConverter userConverter() {
        return new UserConverter();
    }
}
