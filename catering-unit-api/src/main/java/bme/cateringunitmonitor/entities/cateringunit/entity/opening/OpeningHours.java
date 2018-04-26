package bme.cateringunitmonitor.entities.cateringunit.entity.opening;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public class OpeningHours implements Serializable {
    private Map<String, OpeningPerDay> opening = new HashMap<String, OpeningPerDay>(7);

    public OpeningHours(Map<String, OpeningPerDay> opening) {
        this.opening = opening;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OpeningHours)) return false;
        OpeningHours that = (OpeningHours) o;
        return Objects.equals(opening, that.opening);
    }

    @Override
    public int hashCode() {

        return Objects.hash(opening);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("OpeningHours{");
        sb.append("opening=").append(opening);
        sb.append('}');
        return sb.toString();
    }
}
