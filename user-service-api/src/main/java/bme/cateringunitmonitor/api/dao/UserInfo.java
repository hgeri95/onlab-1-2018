package bme.cateringunitmonitor.api.dao;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@Entity
public class UserInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private final String username;

    @Column(nullable = false)
    private final String fullName;

    private final String city;

    @Email
    @Column(nullable = false)
    private final String email;

    private final LocalDate birthDate;

    @Column(nullable = false)
    private final Gender gender;

    public UserInfo(String username, String fullName, String city, @Email String email, LocalDate birthDate, Gender gender) {
        this.username = username;
        this.fullName = fullName;
        this.city = city;
        this.email = email;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public UserInfo(Long id, UserInfo userInfo) {
        this.id = id;
        this.username = userInfo.getUsername();
        this.fullName = userInfo.getFullName();
        this.city = userInfo.getCity();
        this.email = userInfo.getEmail();
        this.birthDate = userInfo.getBirthDate();
        this.gender = userInfo.getGender();
    }
}
