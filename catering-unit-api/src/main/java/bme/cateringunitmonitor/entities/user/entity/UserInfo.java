package bme.cateringunitmonitor.entities.user.entity;

import bme.cateringunitmonitor.entities.BaseEntity;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class UserInfo extends BaseEntity {

    private String username;

    private String firstName;

    private String lastName;

    private String address;

    private String email;

    private Date birthDate;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
