package bme.cateringunitmonitor.api.remoting.service;

import bme.cateringunitmonitor.api.dao.UserDAO;
import bme.cateringunitmonitor.api.dao.UserInfoDAO;

public interface IUserService {

    public static final String REMOTE_ENDPOINT = "/remoteUserService";

    public UserDAO create(UserDAO user);

    public int delete(UserDAO user);

    public UserDAO login(UserDAO user);

    public UserDAO findUser(String username);

    public UserDAO findUserById(Long id);

    public UserInfoDAO saveUserInfo(UserInfoDAO userInfo);

    public UserInfoDAO getUserInfo(String username);

    public UserInfoDAO updateUserInfo(UserInfoDAO userInfo);
}
