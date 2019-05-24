package bme.cateringunitmonitor.api;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Address implements Serializable {
    @NotBlank
    private String address;
    private Coordinate coordinate;
    private String otherInformation;
}
