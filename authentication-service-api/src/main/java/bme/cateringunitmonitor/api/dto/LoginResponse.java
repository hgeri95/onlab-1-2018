package bme.cateringunitmonitor.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class LoginResponse implements Serializable {

    private String accessToken;

    private LocalDateTime tokenExpireDate;

    private String refreshToken;

    private LocalDateTime refreshTokenExpireDate;
}
