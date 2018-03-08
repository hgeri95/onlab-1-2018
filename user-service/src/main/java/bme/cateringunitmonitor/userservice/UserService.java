package bme.cateringunitmonitor.userservice;

import bme.cateringunitmonitor.entities.user.entity.User;
import bme.cateringunitmonitor.userservice.exception.UserServiceException;
import bme.cateringunitmonitor.userservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Service
public class UserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public User create(User user) {
        logger.debug("User to create: {}", user.getUsername());
        if (userRepository.exists(user)) {
            throw new UserServiceException("User already exist.");
        }

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        if (!violations.isEmpty()) {
            throw new IllegalArgumentException(violations.toString());
        }

        String encodedPassworg = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassworg);

        logger.debug("User {} created", user.getUsername());
        return user;
    }

    public User login(User user) {
        logger.debug("Login user: {}", user.getUsername());
        User savedUser = userRepository.findByUsername(user.getUsername());
        if (savedUser != null) {
            if (passwordEncoder.matches(user.getPassword(), savedUser.getPassword())) {
                return savedUser;
            } else {
                throw new BadCredentialsException("Incorrect password for user: " + user.getUsername());
            }
        } else {
            throw new BadCredentialsException("User does not exist: " + user.getUsername());
        }
    }
}
