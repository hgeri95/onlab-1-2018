package bme.cateringunitmonitor.api.dao;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
public class CateringUnit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    @Lob
    @Column(length = 100000)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<OpeningPerDay> openingHours = new ArrayList<>();

    private Address address;

    @ElementCollection(fetch = FetchType.EAGER)
    @Lob
    @Column(length = 100000)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<CategoryParameter> categoryParameters = new ArrayList<>();

    public CateringUnit() {
    }

    @JsonCreator
    public CateringUnit(
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

    //TODO pictures
}
