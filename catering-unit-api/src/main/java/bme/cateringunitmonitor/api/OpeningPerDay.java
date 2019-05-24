package bme.cateringunitmonitor.api;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OpeningPerDay implements Serializable {
    @NotNull
    private WeekDays weekDay;
    @NotBlank
    private String open;
    @NotBlank
    private String close;
}
