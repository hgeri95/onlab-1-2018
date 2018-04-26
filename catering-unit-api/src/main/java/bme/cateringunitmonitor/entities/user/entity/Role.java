package bme.cateringunitmonitor.entities.user.entity;

import java.util.Arrays;
import java.util.List;

public enum Role {
    ROLE_ADMIN(Values.ROLE_ADMIN),
    ROLE_OWNER(Values.ROLE_OWNER),
    ROLE_USER(Values.ROLE_USER);

    public class Values {
        public static final String ROLE_ADMIN = "ROLE_ADMIN";
        public static final String ROLE_OWNER = "ROLE_OWNER";
        public static final String ROLE_USER = "ROLE_USER";
    }

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static List<String> getAllRoles() {
        return Arrays.asList(ROLE_ADMIN.toString(), ROLE_OWNER.toString(), ROLE_USER.toString());
    }
}
