package bme.cateringunitmonitor.userservice.service;

import bme.cateringunitmonitor.api.Role;
import bme.cateringunitmonitor.api.dao.UserDAO;
import bme.cateringunitmonitor.api.dao.UserInfoDAO;
import bme.cateringunitmonitor.api.exception.UserServiceException;
import bme.cateringunitmonitor.api.remoting.service.IUserService;
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
        create(new UserDAO("admin", "12345", Collections.singletonList(Role.ROLE_ADMIN.toString())));
        logger.info("\n////////\n////////\nADMIN USER CREATED: admin 12345\n////////\n////////");
    }

    public UserDAO create(UserDAO user) {
        logger.debug("UserDAO to create: {}", user.getUsername());
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UserServiceException("UserDAO already exists.");
        }

        Set<ConstraintViolation<UserDAO>> violations = validator.validate(user);

        if (!violations.isEmpty()) {
            throw new IllegalArgumentException(violations.toString());
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        logger.debug("UserDAO {} created", user.getUsername());
        return userRepository.save(user);
    }

    public int delete(UserDAO user) {
        logger.debug("UserDAO to delete: {}", user.getUsername());

        if (userRepository.findByUsername(user.getUsername()) != null) {
            logger.debug("UserDAO {} deleted", user.getUsername());
            return userRepository.deleteByUsername(user.getUsername());
        } else {
            throw new UserServiceException("UserDAO does not exist: " + user.getUsername());
        }
    }

    public UserDAO login(UserDAO user) {
        logger.debug("Login user: {}", user.getUsername());
        UserDAO savedUser = userRepository.findByUsername(user.getUsername());

        if (savedUser != null) {
            if (passwordEncoder.matches(user.getPassword(), savedUser.getPassword())) {
                return savedUser;
            } else {
                throw new BadCredentialsException("Incorrect password for user: " + user.getUsername());
            }
        } else {
            throw new BadCredentialsException("UserDAO does not exists: " + user.getUsername());
        }
    }

    public UserDAO findUser(String username) {
        return userRepository.findByUsername(username);
    }

    public UserDAO findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserInfoDAO saveUserInfo(UserInfoDAO userInfo) {
        return userInfoRepository.save(userInfo);
    }

    public UserInfoDAO getUserInfo(String username) {
        return userInfoRepository.findByUsername(username);
    }

    public UserInfoDAO updateUserInfo(UserInfoDAO userInfo) {
        logger.debug("UserDAO info to update: {}", userInfo);
        if (userInfoRepository.existsByUsername(userInfo.getUsername())) {
            UserInfoDAO userInfoToUpdate = userInfoRepository.findByUsername(userInfo.getUsername());
            UserInfoDAO updatedUserInfo = new UserInfoDAO(userInfoToUpdate.getId(), userInfo);
            return userInfoRepository.save(updatedUserInfo);
        } else {
            return userInfoRepository.save(userInfo);
        }
    }
}
