package bme.cateringunitmonitor.userservice.service;

import bme.cateringunitmonitor.api.Role;
import bme.cateringunitmonitor.api.dto.UserDTO;
import bme.cateringunitmonitor.api.dto.UserInfoDTO;
import bme.cateringunitmonitor.api.exception.UserServiceException;
import bme.cateringunitmonitor.api.remoting.service.IUserService;
import bme.cateringunitmonitor.userservice.dao.UserDAO;
import bme.cateringunitmonitor.userservice.dao.UserInfoDAO;
import bme.cateringunitmonitor.userservice.repository.UserInfoRepository;
import bme.cateringunitmonitor.userservice.repository.UserRepository;
import bme.cateringunitmonitor.userservice.util.UserConverter;
import bme.cateringunitmonitor.userservice.util.UserInfoConverter;
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

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private UserInfoConverter userInfoConverter;

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @PostConstruct
    public void createDefaultAdminUser() {
        create(new UserDTO("admin", "12345", Collections.singletonList(Role.ROLE_ADMIN)));
        logger.info("\n////////\n////////\nADMIN USER CREATED: admin 12345\n////////\n////////");
    }

    public UserDTO create(UserDTO user) {
        logger.debug("User to create: {}", user.getUsername());
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UserServiceException("User already exists.");
        }

        Set<ConstraintViolation<UserDTO>> violations = validator.validate(user);

        if (!violations.isEmpty()) {
            throw new IllegalArgumentException(violations.toString());
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        UserDAO userDAO = userConverter.convertToEntity(user);
        logger.debug("UserDAO {} created", userDAO.getUsername());
        return userConverter.convertToDTO(userRepository.save(userDAO));
    }

    public int delete(String username) {
        logger.debug("User to delete: {}", username);

        if (userRepository.findByUsername(username) != null) {
            logger.debug("UserDAO {} deleted", username);
            return userRepository.deleteByUsername(username);
        } else {
            throw new UserServiceException("User does not exist: " + username);
        }
    }

    public UserDTO login(UserDTO user) {
        logger.debug("Login user: {}", user.getUsername());
        UserDAO savedUser = userRepository.findByUsername(user.getUsername());

        if (savedUser != null) {
            if (passwordEncoder.matches(user.getPassword(), savedUser.getPassword())) {
                return userConverter.convertToDTO(savedUser);
            } else {
                throw new BadCredentialsException("Incorrect password for user: " + user.getUsername());
            }
        } else {
            throw new BadCredentialsException("UserDAO does not exists: " + user.getUsername());
        }
    }

    public UserDTO findUser(String username) {
        return userConverter.convertToDTO(userRepository.findByUsername(username));
    }

    public UserDTO findUserById(Long id) {
        return userConverter.convertToDTO(userRepository.findById(id).orElse(null));
    }

    public UserInfoDTO saveUserInfo(UserInfoDTO userInfo) {
        logger.debug("Save userinfo for user: {}", userInfo.getUsername());
        UserInfoDAO userInfoDAO = userInfoRepository.save(userInfoConverter.convertToEntity(userInfo));
        return userInfoConverter.convertToDTO(userInfoDAO);
    }

    public UserInfoDTO getUserInfo(String username) {
        return userInfoConverter.convertToDTO(userInfoRepository.findByUsername(username));
    }

    public UserInfoDTO updateUserInfo(UserInfoDTO userInfo) {
        logger.debug("User info to update: {}", userInfo);
        if (userInfoRepository.existsByUsername(userInfo.getUsername())) {
            UserInfoDAO userInfoToUpdate = userInfoRepository.findByUsername(userInfo.getUsername());
            UserInfoDAO userInfoRequest = userInfoConverter.convertToEntity(userInfo);
            UserInfoDAO updatedUserInfo = new UserInfoDAO(userInfoToUpdate.getId(), userInfoRequest);
            return userInfoConverter.convertToDTO(userInfoRepository.save(updatedUserInfo));
        } else {
            return userInfoConverter.convertToDTO(userInfoRepository.save(userInfoConverter.convertToEntity(userInfo)));
        }
    }
}
