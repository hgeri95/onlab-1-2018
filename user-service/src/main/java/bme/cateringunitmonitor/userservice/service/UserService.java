package bme.cateringunitmonitor.userservice.service;

import bme.cateringunitmonitor.api.Role;
import bme.cateringunitmonitor.api.dto.UserDTO;
import bme.cateringunitmonitor.api.dto.UserInfoDTO;
import bme.cateringunitmonitor.api.dto.UserInfoRequest;
import bme.cateringunitmonitor.api.dto.UserRequest;
import bme.cateringunitmonitor.api.exception.UserServiceException;
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
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserService {

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
    public void createDefaultAdminUser() throws UserServiceException {
        create(new UserRequest("admin", "12345", Collections.singletonList(Role.ROLE_ADMIN)));
        logger.info("\n////////\n////////\nADMIN USER CREATED: admin 12345\n////////\n////////");
    }

    public UserDTO create(UserRequest userRequest) throws UserServiceException, IllegalArgumentException {
        logger.debug("User to create: {}", userRequest.getUsername());

        if (userRepository.findByUsername(userRequest.getUsername()) != null) {
            throw new UserServiceException("User already exists!");
        }

        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);

        if (!violations.isEmpty()) {
            throw new IllegalArgumentException(violations.toString());
        }

        String encodedPassword = passwordEncoder.encode(userRequest.getPassword());
        userRequest.setPassword(encodedPassword);

        UserDAO userDAO = userConverter.convertToEntity(userRequest);
        logger.debug("UserDAO for user {} created", userDAO.getUsername());
        return userConverter.convertToDTO(userRepository.save(userDAO));
    }

    public int delete(String username) throws UserServiceException {
        logger.debug("User to delete: {}", username);

        if (userRepository.findByUsername(username) != null) {
            logger.debug("UserDAO {} deleted", username);
            userInfoRepository.deleteByUsername(username);
            return userRepository.deleteByUsername(username);
        } else {
            throw new UserServiceException("User does not exist: " + username);
        }
    }

    public UserDTO login(UserDTO user) throws BadCredentialsException {
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

    public UserInfoDTO saveUserInfo(UserInfoRequest userInfo) {
        logger.debug("Save user info for user: {}", userInfo.getUsername());
        UserInfoDAO userInfoDAO = userInfoRepository.save(userInfoConverter.convertToEntity(userInfo));
        return userInfoConverter.convertToDTO(userInfoDAO);
    }

    public UserInfoDTO getUserInfo(String username) throws UserServiceException {
        logger.debug("Find user info for user: {}", username);
        Optional<UserInfoDAO> userInfo = userInfoRepository.findUserInfoByUsername(username);

        if (userInfo.isPresent()) {
            return userInfoConverter.convertToDTO(userInfo.get());
        } else {
            throw new UserServiceException("User info does not exists!");
        }
    }

    public UserInfoDTO updateUserInfo(UserInfoRequest userInfoRequest) {
        logger.debug("User info to update: {}", userInfoRequest);
        Optional<UserInfoDAO> userInfoToUpdate = userInfoRepository
                .findUserInfoByUsername(userInfoRequest.getUsername());
        if (userInfoToUpdate.isPresent()) {
            logger.debug("Update user info for user: {}", userInfoRequest.getUsername());
            UserInfoDAO userInfo = userInfoConverter.convertToEntity(userInfoRequest);
            UserInfoDAO updatedUserInfo = new UserInfoDAO(userInfoToUpdate.get().getId(), userInfo);
            return userInfoConverter.convertToDTO(userInfoRepository.save(updatedUserInfo));
        } else {
            logger.debug("Create new user info for user: {}", userInfoRequest.getUsername());
            return saveUserInfo(userInfoRequest);
        }
    }
}
