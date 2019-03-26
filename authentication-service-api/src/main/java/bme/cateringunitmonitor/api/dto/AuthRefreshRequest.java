package bme.cateringunitmonitor.api.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class AuthRefreshRequest implements Serializable {
    private final Long userId;
    private final String refreshToken;
}
