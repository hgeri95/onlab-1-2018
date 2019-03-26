package bme.cateringunitmonitor.api.wrapper;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class RefreshToken implements Serializable {
    private String refreshToken;
    private LocalDateTime refreshTokenExpireDate;
}
