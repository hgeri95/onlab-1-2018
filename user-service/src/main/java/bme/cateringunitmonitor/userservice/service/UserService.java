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
import bme.cateringunitmonitor.utils.amqp.EventTypes;
import bme.cateringunitmonitor.utils.amqp.GenericEvent;
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
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Autowired(required = false)//Optional because of testing only
    private Optional<EventSender> eventSender;

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @PostConstruct
    public void createDefaultAdminUser() throws UserServiceException {
        if (userRepository.findByUsername("admin") != null) {
            logger.info("\n////////\n////////\nADMIN USER ALREADY EXISTS\n////////\n////////");
        } else {
            create(new UserRequest("admin", "12345", Collections.singletonList(Role.ROLE_ADMIN)));
            logger.info("\n////////\n////////\nADMIN USER CREATED: admin 12345\n////////\n////////");
        }
        if (userRepository.findByUsername("technical") != null) {
            logger.info("\n////////\n////////\nTECHNICAL USER ALREADY EXISTS\n////////\n////////");
        } else {
            //Create technical user
            create(new UserRequest("technical", "12345", Collections.singletonList(Role.ROLE_TECHNICAL)));
            logger.info("\n////////\n////////\nTECHNICAL USER CREATED: technical 12345\n////////\n////////");
        }
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
            sendDeleteUserEvent(username);
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

    public boolean isUserExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public UserDTO findUserById(Long id) {
        return userConverter.convertToDTO(userRepository.findById(id).orElse(null));
    }

    public UserInfoDTO saveUserInfo(UserInfoRequest userInfo) throws UserServiceException {
        logger.debug("Save user info for user: {}", userInfo.getUsername());
        if (!userInfoRepository.findUserInfoByUsername(userInfo.getUsername()).isPresent()) {
            UserInfoDAO userInfoDAO = userInfoRepository.save(userInfoConverter.convertToEntity(userInfo));
            return userInfoConverter.convertToDTO(userInfoDAO);
        } else {
            throw new UserServiceException("User info is exists!");
        }
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
            return userInfoConverter.convertToDTO(
                    userInfoRepository.save(
                            userInfoConverter.convertToEntity(userInfoRequest)));
        }
    }

    public List<UserInfoDTO> getUserInfos(List<String> usernames) {
        logger.debug("Get user infos for users: {}", usernames);
        List<UserInfoDAO> userInfos = userInfoRepository.findByUsernameIn(usernames);
        logger.debug("User infos found: {}", userInfos.size());
        return userInfos.stream().map(i -> userInfoConverter.convertToDTO(i)).collect(Collectors.toList());
    }

    private void sendDeleteUserEvent(String username) {
        GenericEvent deleteUserEvent = new GenericEvent(EventTypes.DELETE_USER_EVENT, "username", username);
        if (eventSender.isPresent()) {
            eventSender.get().send(deleteUserEvent.getMessage());
        } else {
            logger.error("Event sender is missing!");
        }
    }
}
