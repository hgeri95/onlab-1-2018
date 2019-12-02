package bme.cateringunitmonitor.api.dto;

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
public class StatisticValuesDTO implements Serializable {

    @NotBlank
    private String type;

    @NotNull
    private Long cateringUnitId;

    private int statistic;

    public void increment() {
        statistic += 1;
    }
}
