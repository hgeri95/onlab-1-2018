package bme.cateringunitmonitor.userservice.util;

import bme.cateringunitmonitor.api.dto.UserInfoDTO;
import bme.cateringunitmonitor.api.dto.UserInfoRequest;
import bme.cateringunitmonitor.userservice.dao.UserInfoDAO;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserInfoConverter {

    @Autowired
    private ModelMapper mapper;

    public UserInfoDAO convertToEntity(UserInfoDTO userInfoDTO) {
        return mapper.map(userInfoDTO, UserInfoDAO.class);
    }

    public UserInfoDAO convertToEntity(UserInfoRequest userInfoRequest) {
        return mapper.map(userInfoRequest, UserInfoDAO.class);
    }

    public UserInfoDTO convertToDTO(UserInfoDAO userInfoDAO) {
        return mapper.map(userInfoDAO, UserInfoDTO.class);
    }
}
