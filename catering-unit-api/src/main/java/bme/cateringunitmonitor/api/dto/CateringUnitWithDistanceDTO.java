package bme.cateringunitmonitor.api.dto;

import bme.cateringunitmonitor.api.Address;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CateringUnitWithDistanceDTO implements Serializable {
    private Long id;

    @NotBlank
    private String name;

    private Address address;

    private double distance;
}
