package bme.cateringunitmonitor.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class SignUpRequest implements Serializable {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private List<String> roles;
}
