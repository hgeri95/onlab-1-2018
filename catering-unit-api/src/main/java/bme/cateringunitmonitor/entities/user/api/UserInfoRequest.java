package bme.cateringunitmonitor.entities.user.api;

import bme.cateringunitmonitor.entities.user.entity.Gender;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
public class UserInfoRequest implements Serializable {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private String address;

    //TODO mail validation
    @NotNull
    private String email;

    private Date birthDate;

    @NotNull
    private Gender gender;

    public UserInfoRequest(@NotNull String firstName, @NotNull String lastName, String address, @NotNull String email, Date birthDate, @NotNull Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserInfoRequest)) return false;
        UserInfoRequest that = (UserInfoRequest) o;
        return Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(address, that.address) &&
                Objects.equals(email, that.email) &&
                Objects.equals(birthDate, that.birthDate) &&
                gender == that.gender;
    }

    @Override
    public int hashCode() {

        return Objects.hash(firstName, lastName, address, email, birthDate, gender);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UserInfoRequest{");
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", birthDate=").append(birthDate);
        sb.append(", gender=").append(gender);
        sb.append('}');
        return sb.toString();
    }
}
