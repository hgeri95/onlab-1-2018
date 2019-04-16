package bme.cateringunitmonitor.api.remoting.service;

import bme.cateringunitmonitor.api.dto.UserDTO;
import bme.cateringunitmonitor.api.dto.UserInfoDTO;

public interface IUserService {

    public static final String REMOTE_ENDPOINT = "/remoteUserService";

    public UserDTO create(UserDTO user);

    public int delete(String username);

    public UserDTO login(UserDTO user);

    public UserDTO findUser(String username);

    public UserDTO findUserById(Long id);

    public UserInfoDTO saveUserInfo(UserInfoDTO userInfo);

    public UserInfoDTO getUserInfo(String username);

    public UserInfoDTO updateUserInfo(UserInfoDTO userInfo);
}
