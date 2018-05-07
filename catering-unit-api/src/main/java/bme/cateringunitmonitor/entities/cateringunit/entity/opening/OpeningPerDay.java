package bme.cateringunitmonitor.entities.cateringunit.entity.opening;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class OpeningPerDay implements Serializable {
    private WeekDays weekDay;
    private String open;
    private String close;

    public OpeningPerDay(WeekDays weekDay, String open, String close) {
        this.weekDay = weekDay;
        this.open = open;
        this.close = close;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OpeningPerDay)) return false;
        OpeningPerDay that = (OpeningPerDay) o;
        return weekDay == that.weekDay &&
                Objects.equals(open, that.open) &&
                Objects.equals(close, that.close);
    }

    @Override
    public int hashCode() {

        return Objects.hash(weekDay, open, close);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("OpeningPerDay{");
        sb.append("weekDay=").append(weekDay);
        sb.append(", open='").append(open).append('\'');
        sb.append(", close='").append(close).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
