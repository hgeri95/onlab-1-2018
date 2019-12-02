package bme.cateringunitmonitor.rating.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class RatingRequest implements Serializable {
    @NotBlank
    private String cateringUnitName;

    @Min(value = 1, message = "Rate should be at least 1")
    @Max(value = 5, message = "Rate could not be higher than 5")
    private int rate;

    private String comment;
}
