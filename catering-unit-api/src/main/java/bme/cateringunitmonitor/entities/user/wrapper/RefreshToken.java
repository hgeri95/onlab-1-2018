package bme.cateringunitmonitor.entities.user.wrapper;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
public class RefreshToken implements Serializable {

    private String refreshToken;
    private Date refreshTokenExpireDate;

    public RefreshToken(String refreshToken, Date refreshTokenExpireDate) {
        this.refreshToken = refreshToken;
        this.refreshTokenExpireDate = refreshTokenExpireDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RefreshToken)) return false;
        RefreshToken that = (RefreshToken) o;
        return Objects.equals(refreshToken, that.refreshToken) &&
                Objects.equals(refreshTokenExpireDate, that.refreshTokenExpireDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(refreshToken, refreshTokenExpireDate);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RefreshToken{");
        sb.append("refreshToken='").append(refreshToken).append('\'');
        sb.append(", refreshTokenExpireDate=").append(refreshTokenExpireDate);
        sb.append('}');
        return sb.toString();
    }
}
