package bme.cateringunitmonitor.api.dto;

import bme.cateringunitmonitor.api.Role;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class UserRequest implements Serializable {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private List<Role> roles;

    public UserRequest(@NotBlank String username, @NotBlank String password, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public UserRequest(@NotBlank String username, @NotBlank String password) {
        this.username = username;
        this.password = password;
    }
}
