package bme.cateringunitmonitor.userservice.dao;

import bme.cateringunitmonitor.api.Gender;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@Entity
public class UserInfoDAO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String fullName;

    private String city;

    @Email
    @Column(nullable = false)
    private String email;

    private LocalDate birthDate;

    @Column(nullable = false)
    private Gender gender;

    public UserInfoDAO() {
    }

    public UserInfoDAO(String username, String fullName, String city, @Email String email, LocalDate birthDate, Gender gender) {
        this.username = username;
        this.fullName = fullName;
        this.city = city;
        this.email = email;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public UserInfoDAO(Long id, UserInfoDAO userInfo) {
        this.id = id;
        this.username = userInfo.getUsername();
        this.fullName = userInfo.getFullName();
        this.city = userInfo.getCity();
        this.email = userInfo.getEmail();
        this.birthDate = userInfo.getBirthDate();
        this.gender = userInfo.getGender();
    }
}
