package bme.cateringunitmonitor.userservice.util;

import bme.cateringunitmonitor.api.dto.UserDTO;
import bme.cateringunitmonitor.api.dto.UserRequest;
import bme.cateringunitmonitor.userservice.dao.UserDAO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UserConverter {

    @Autowired
    private ModelMapper mapper;

    public UserDAO convertToEntity(UserDTO userDTO) {
        return mapper.map(userDTO, UserDAO.class);
    }

    public UserDAO convertToEntity(UserRequest userRequest) {
        return mapper.map(userRequest, UserDAO.class);
    }

    public UserDTO convertToDTO(UserDAO userDAO) {
        return mapper.map(userDAO, UserDTO.class);
    }
}
