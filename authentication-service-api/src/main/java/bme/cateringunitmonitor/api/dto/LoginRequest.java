package bme.cateringunitmonitor.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class LoginRequest implements Serializable{

    private String username;

    private String password;
}
