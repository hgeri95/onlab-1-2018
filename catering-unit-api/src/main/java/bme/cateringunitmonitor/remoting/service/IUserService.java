package bme.cateringunitmonitor.remoting.service;

import bme.cateringunitmonitor.entities.user.entity.User;
import bme.cateringunitmonitor.entities.user.entity.UserInfo;

public interface IUserService {

    String REMOTE_ENDPOINT = "/remoteUserService";

    public User create(User user);

    public int delete(User user);

    public User login(User user);

    public User findUser(String username);

    public UserInfo setUserInfo(UserInfo userInfo);

    public UserInfo getUserInfo(String username);
}
