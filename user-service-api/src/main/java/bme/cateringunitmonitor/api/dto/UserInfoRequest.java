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
public class UserInfoRequest implements Serializable {

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
}
