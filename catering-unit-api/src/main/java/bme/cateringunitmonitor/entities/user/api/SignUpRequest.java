package bme.cateringunitmonitor.entities.user.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class SignUpRequest implements Serializable {

    @NotNull
    private String username;

    @NotNull
    private String password;

    private List<String> roles;

    @JsonCreator
    public SignUpRequest(
            @JsonProperty("username") String username,
            @JsonProperty("password") String password,
            @JsonProperty("roles") List<String> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}
