package bme.cateringunitmonitor.entities.user.api;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class AuthRefreshRequest implements Serializable {

    private String username;
    private String refreshToken;

    public AuthRefreshRequest(String username, String refreshToken) {
        this.username = username;
        this.refreshToken = refreshToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthRefreshRequest)) return false;
        AuthRefreshRequest that = (AuthRefreshRequest) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(refreshToken, that.refreshToken);
    }

    @Override
    public int hashCode() {

        return Objects.hash(username, refreshToken);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AuthRefreshRequest{");
        sb.append("username='").append(username).append('\'');
        sb.append(", refreshToken='").append(refreshToken).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
