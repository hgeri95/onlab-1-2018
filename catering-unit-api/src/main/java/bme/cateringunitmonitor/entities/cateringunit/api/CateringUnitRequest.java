package bme.cateringunitmonitor.entities.cateringunit.api;

import bme.cateringunitmonitor.entities.cateringunit.entity.address.Address;
import bme.cateringunitmonitor.entities.cateringunit.entity.category.CategoryParameters;
import bme.cateringunitmonitor.entities.cateringunit.entity.opening.OpeningHours;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class CateringUnitRequest implements Serializable {

    private String name;

    private String description;

    private OpeningHours openingHours;

    private Address address;

    private CategoryParameters categoryParameters;

    public CateringUnitRequest(String name, String description, OpeningHours openingHours, Address address,
                               CategoryParameters categoryParameters) {
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
