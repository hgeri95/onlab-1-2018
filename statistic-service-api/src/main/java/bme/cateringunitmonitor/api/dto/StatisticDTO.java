package bme.cateringunitmonitor.api.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StatisticDTO implements Serializable {

    @NotNull
    private Long cateringUnitId;

    @NotBlank
    private String username;

    @NotNull
    private LocalDateTime creation;

    @NotBlank
    private String type;
}
