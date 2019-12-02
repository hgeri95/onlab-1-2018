package bme.cateringunitmonitor.api.dto;

import bme.cateringunitmonitor.api.Address;
import bme.cateringunitmonitor.api.CategoryParameter;
import bme.cateringunitmonitor.api.OpeningPerDay;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CateringUnitDTO implements Serializable {

    private Long id;

    @NotBlank
    private String name;

    private String description;

    private String owner;

    @Valid
    private List<OpeningPerDay> openingHours;

    @NotNull
    @Valid
    private Address address;

    @Valid
    private List<CategoryParameter> categoryParameters;
}
