package bme.cateringunitmonitor.api.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AuthRefreshRequest implements Serializable {
    private Long userId;
    private String refreshToken;
}
