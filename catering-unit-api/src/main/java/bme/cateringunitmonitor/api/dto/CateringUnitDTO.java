package bme.cateringunitmonitor.api.dto;

import bme.cateringunitmonitor.api.Address;
import bme.cateringunitmonitor.api.CategoryParameter;
import bme.cateringunitmonitor.api.OpeningPerDay;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CateringUnitDTO {

    private Long id;

    private String name;

    private String description;

    private List<OpeningPerDay> openingHours;

    private Address address;

    private List<CategoryParameter> categoryParameters;
}
