package bme.cateringunitmonitor.api.dto;

import bme.cateringunitmonitor.api.dao.Gender;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class UserInfoRequest implements Serializable {

    @NotBlank
    private String fullName;

    private String city;

    @Email
    @NotBlank
    private String email;

    private LocalDate birthDate;

    @NotNull
    private Gender gender;
}
