package bme.cateringunitmonitor.entities.user.api;

import bme.cateringunitmonitor.entities.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class LoginResponse implements Serializable {

    private String accessToken;

    private Date tokenExpireDate;

    private String refreshToken;

    private Date refreshTokenExpireDate;

    private User user;

    public LoginResponse(String accessToken, Date tokenExpireDate,
                         String refreshToken, Date refreshTokenExpireDate, User user) {
        this.accessToken = accessToken;
        this.tokenExpireDate = tokenExpireDate;
        this.refreshToken = refreshToken;
        this.refreshTokenExpireDate = refreshTokenExpireDate;
        this.user = user;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("LoginResponse{");
        sb.append("accessToken='").append(accessToken).append('\'');
        sb.append(", tokenExpireDate=").append(tokenExpireDate);
        sb.append(", refreshToken='").append(refreshToken).append('\'');
        sb.append(", refreshTokenExpireDate=").append(refreshTokenExpireDate);
        sb.append(", user=").append(user);
        sb.append('}');
        return sb.toString();
    }
}
