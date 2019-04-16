package bme.cateringunitmonitor.api.dto;

import bme.cateringunitmonitor.api.Gender;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserInfoDTO implements Serializable {

    private Long id;

    private String username;

    @NotBlank
    private String fullName;

    private String city;

    @Email
    @NotBlank
    private String email;

    private LocalDate birthDate;

    @NotNull
    private Gender gender;

    public UserInfoDTO(String username, String fullName, String city, String email, LocalDate birthDate, Gender gender) {
        this.username = username;
        this.fullName = fullName;
        this.city = city;
        this.email = email;
        this.birthDate = birthDate;
        this.gender = gender;
    }
}
