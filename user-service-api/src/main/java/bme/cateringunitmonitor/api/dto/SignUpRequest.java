package bme.cateringunitmonitor.api.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class SignUpRequest implements Serializable {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private List<String> roles;
}
