package bme.cateringunitmonitor.api;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class OpeningPerDay implements Serializable {
    private WeekDays weekDay;
    private String open;
    private String close;
}