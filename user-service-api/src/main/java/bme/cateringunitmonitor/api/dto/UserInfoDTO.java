package bme.cateringunitmonitor.api.dto;

import bme.cateringunitmonitor.api.Gender;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UserInfoDTO implements Serializable {

    private Long id;

    private String username;

    @NotBlank
    private String fullName;

    @NotBlank
    private String city;

    @Email
    @NotBlank
    private String email;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    private LocalDate birthDate;

    @NotNull
    private Gender gender;

    public UserInfoDTO(String username, @NotBlank String fullName, String city, @Email @NotBlank String email,
                       LocalDate birthDate, @NotNull Gender gender) {
        this.username = username;
        this.fullName = fullName;
        this.city = city;
        this.email = email;
        this.birthDate = birthDate;
        this.gender = gender;
    }
}
