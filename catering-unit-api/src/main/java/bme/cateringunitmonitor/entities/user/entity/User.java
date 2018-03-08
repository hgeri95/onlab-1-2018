package bme.cateringunitmonitor.entities.user.entity;

import bme.cateringunitmonitor.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.management.relation.Role;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class User extends BaseEntity {

    @Column(nullable = false, unique = true)
    @NotNull
    private String username;

    @NotNull
    private String password;

    private List<Role> roles = new ArrayList<Role>();

    public User() {
    }

    public User(String username, String password, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}
