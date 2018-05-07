package bme.cateringunitmonitor.userservice.service;

import bme.cateringunitmonitor.entities.exception.UserServiceException;
import bme.cateringunitmonitor.entities.user.entity.Role;
import bme.cateringunitmonitor.entities.user.entity.User;
import bme.cateringunitmonitor.entities.user.entity.UserInfo;
import bme.cateringunitmonitor.remoting.service.IUserService;
import bme.cateringunitmonitor.userservice.repository.UserInfoRepository;
import bme.cateringunitmonitor.userservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Collections;
import java.util.Set;

@Service
public class UserService implements IUserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @PostConstruct
    public void createDefaultAdminUser() {
        create(new User("admin", "12345", Collections.singletonList(Role.ROLE_ADMIN.toString())));
        logger.info("\n////////\n////////\nADMIN USER CREATED: admin 12345\n////////\n////////");
    }

    public User create(User user) {
        logger.debug("User to create: {}", user.getUsername());
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UserServiceException("User already exists.");
        }

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        if (!violations.isEmpty()) {
            throw new IllegalArgumentException(violations.toString());
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        logger.debug("User {} created", user.getUsername());
        return userRepository.save(user);
    }

    public int delete(User user) {
        logger.debug("User to delete: {}", user.getUsername());

        if (userRepository.findByUsername(user.getUsername()) != null) {
            logger.debug("User {} deleted", user.getUsername());
            return userRepository.deleteByUsername(user.getUsername());
        } else {
            throw new UserServiceException("User does not exist: " + user.getUsername());
        }
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

    public User findUser(String username) {
        return userRepository.findByUsername(username);
    }

    public UserInfo setUserInfo(UserInfo userInfo) {
        return userInfoRepository.save(userInfo);
    }

    public UserInfo getUserInfo(String username) {
        return userInfoRepository.findByUsername(username);
    }
}
