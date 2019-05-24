package bme.cateringunitmonitor.api.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse implements Serializable {

    private String accessToken;

    private LocalDateTime tokenExpireDate;

    private String refreshToken;

    private LocalDateTime refreshTokenExpireDate;
}
