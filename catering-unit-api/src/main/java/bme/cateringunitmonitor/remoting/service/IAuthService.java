package bme.cateringunitmonitor.remoting.service;

import bme.cateringunitmonitor.entities.user.api.AuthRefreshRequest;
import bme.cateringunitmonitor.entities.user.api.LoginRequest;
import bme.cateringunitmonitor.entities.user.api.LoginResponse;

public interface IAuthService {

    String REMOTE_ENDPOINT = "/remoteAuthService";

    public LoginResponse authenticate(LoginRequest loginRequest);
    public void logout(String username);
    public LoginResponse refresh(AuthRefreshRequest refreshRequest);
}
