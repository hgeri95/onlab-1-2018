package bme.cateringunitmonitor.entities.cateringunit.entity.address;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class Address implements Serializable {
    private String country;
    private String street;
    private int number;
    private String otherInformation;

    @JsonCreator
    public Address(
            @JsonProperty("country") String country,
            @JsonProperty("street") String street,
            @JsonProperty("number") int number,
            @JsonProperty("otherInformation") String otherInformation) {
        this.country = country;
        this.street = street;
        this.number = number;
        this.otherInformation = otherInformation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return number == address.number &&
                Objects.equals(country, address.country) &&
                Objects.equals(street, address.street) &&
                Objects.equals(otherInformation, address.otherInformation);
    }

    @Override
    public int hashCode() {

        return Objects.hash(country, street, number, otherInformation);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Address{");
        sb.append("country='").append(country).append('\'');
        sb.append(", street='").append(street).append('\'');
        sb.append(", number=").append(number);
        sb.append(", otherInformation='").append(otherInformation).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
