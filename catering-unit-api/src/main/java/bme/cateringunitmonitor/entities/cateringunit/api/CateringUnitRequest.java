package bme.cateringunitmonitor.entities.cateringunit.api;

import bme.cateringunitmonitor.entities.cateringunit.entity.address.Address;
import bme.cateringunitmonitor.entities.cateringunit.entity.category.CategoryParameter;
import bme.cateringunitmonitor.entities.cateringunit.entity.opening.OpeningPerDay;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class CateringUnitRequest implements Serializable {

    private String name;

    private String description;

    private List<OpeningPerDay> openingHours;

    private Address address;

    private List<CategoryParameter> categoryParameters;

    @JsonCreator
    public CateringUnitRequest(
            @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("openingHours") List<OpeningPerDay> openingHours,
            @JsonProperty("address") Address address,
            @JsonProperty("categoryParameters") List<CategoryParameter> categoryParameters) {
        this.name = name;
        this.description = description;
        this.openingHours = openingHours;
        this.address = address;
        this.categoryParameters = categoryParameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CateringUnitRequest)) return false;
        CateringUnitRequest that = (CateringUnitRequest) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(openingHours, that.openingHours) &&
                Objects.equals(address, that.address) &&
                Objects.equals(categoryParameters, that.categoryParameters);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, description, openingHours, address, categoryParameters);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CateringUnitRequest{");
        sb.append("name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", openingHours=").append(openingHours);
        sb.append(", address=").append(address);
        sb.append(", categoryParameters=").append(categoryParameters);
        sb.append('}');
        return sb.toString();
    }
}
