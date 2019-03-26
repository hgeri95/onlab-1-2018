package bme.cateringunitmonitor.api.dto;

import bme.cateringunitmonitor.api.dao.Address;
import bme.cateringunitmonitor.api.dao.CategoryParameter;
import bme.cateringunitmonitor.api.dao.OpeningPerDay;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CateringUnitRequest implements Serializable {

    private String name;

    private String description;

    private List<OpeningPerDay> openingHours;

    private Address address;

    private List<CategoryParameter> categoryParameters;
}
