package bme.cateringunitmonitor.api.remoting.service;

import bme.cateringunitmonitor.api.dao.User;
import bme.cateringunitmonitor.api.dao.UserInfo;

public interface IUserService {

    public static final String REMOTE_ENDPOINT = "/remoteUserService";

    public User create(User user);

    public int delete(User user);

    public User login(User user);

    public User findUser(String username);

    public User findUserById(Long id);

    public UserInfo saveUserInfo(UserInfo userInfo);

    public UserInfo getUserInfo(String username);

    public UserInfo updateUserInfo(UserInfo userInfo);
}
