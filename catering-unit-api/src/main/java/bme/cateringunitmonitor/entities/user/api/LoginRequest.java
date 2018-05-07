package bme.cateringunitmonitor.entities.user.api;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class LoginRequest implements Serializable{

    private String username;

    private String password;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("LoginRequest{");
        sb.append("username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
