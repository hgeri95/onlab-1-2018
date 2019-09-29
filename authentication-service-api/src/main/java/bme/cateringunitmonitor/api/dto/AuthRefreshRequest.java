package bme.cateringunitmonitor.api.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AuthRefreshRequest implements Serializable {
    @NotNull
    private Long userId;
    @NotBlank
    private String refreshToken;
}
