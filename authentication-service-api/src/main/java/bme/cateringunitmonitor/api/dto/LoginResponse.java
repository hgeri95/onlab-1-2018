package bme.cateringunitmonitor.api.dto;

import lombok.*;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse implements Serializable {

    private String accessToken;

    private OffsetDateTime tokenExpireDate;

    private String refreshToken;

    private OffsetDateTime refreshTokenExpireDate;
}
