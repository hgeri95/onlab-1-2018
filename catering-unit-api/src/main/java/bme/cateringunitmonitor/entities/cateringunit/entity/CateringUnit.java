package bme.cateringunitmonitor.entities.cateringunit.entity;

import bme.cateringunitmonitor.entities.cateringunit.entity.address.Address;
import bme.cateringunitmonitor.entities.cateringunit.entity.category.CategoryParameter;
import bme.cateringunitmonitor.entities.cateringunit.entity.opening.OpeningHours;
import bme.cateringunitmonitor.entities.cateringunit.entity.opening.OpeningPerDay;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Getter
@Setter
@Entity
public class CateringUnit implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @ElementCollection
    private List<OpeningPerDay> openingHours;

    private Address address;

    @ElementCollection
    private List<CategoryParameter> categoryParameters;

    public CateringUnit(String name, String description, List<OpeningPerDay> openingHours, Address address, List<CategoryParameter> categoryParameters) {
        this.name = name;
        this.description = description;
        this.openingHours = openingHours;
        this.address = address;
        this.categoryParameters = categoryParameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CateringUnit)) return false;
        CateringUnit that = (CateringUnit) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(openingHours, that.openingHours) &&
                Objects.equals(address, that.address) &&
                Objects.equals(categoryParameters, that.categoryParameters);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, description, openingHours, address, categoryParameters);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CateringUnit{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", openingHours=").append(openingHours);
        sb.append(", address=").append(address);
        sb.append(", categoryParameters=").append(categoryParameters);
        sb.append('}');
        return sb.toString();
    }

    //TODO pictures
}
