package bme.cateringunitmonitor.api.dto;

import bme.cateringunitmonitor.api.Role;
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

    private OffsetDateTime refreshTokenExpireDate;//TODO localdatetime

    private String username;

    private Role role;
}
