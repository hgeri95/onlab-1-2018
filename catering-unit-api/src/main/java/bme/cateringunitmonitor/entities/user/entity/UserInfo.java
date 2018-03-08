package bme.cateringunitmonitor.entities.user.entity;

import bme.cateringunitmonitor.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Getter
@Setter
@Entity
public class UserInfo extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String address;

    //TODO mail validation
    @Column(nullable = false)
    private String email;

    private Date birthDate;

    @Column(nullable = false)
    private Gender gender;

    public UserInfo() {
    }

    public UserInfo(String username, String firstName, String lastName, String address, String email,
        Date birthDate, Gender gender) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.birthDate = birthDate;
        this.gender = gender;
    }
}
