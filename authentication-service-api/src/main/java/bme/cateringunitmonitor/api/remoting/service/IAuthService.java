package bme.cateringunitmonitor.api.remoting.service;

import bme.cateringunitmonitor.api.dto.AuthRefreshRequest;
import bme.cateringunitmonitor.api.dto.LoginRequest;
import bme.cateringunitmonitor.api.dto.LoginResponse;

public interface IAuthService {

    String REMOTE_ENDPOINT = "/remoteAuthService";

    public LoginResponse authenticate(LoginRequest loginRequest);
    public void logout(String username);
    public LoginResponse refresh(AuthRefreshRequest refreshRequest);
}
