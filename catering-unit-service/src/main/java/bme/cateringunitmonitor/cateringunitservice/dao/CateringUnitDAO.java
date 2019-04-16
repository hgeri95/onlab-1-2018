package bme.cateringunitmonitor.cateringunitservice.dao;

import bme.cateringunitmonitor.api.Address;
import bme.cateringunitmonitor.api.CategoryParameter;
import bme.cateringunitmonitor.api.OpeningPerDay;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Entity
public class CateringUnitDAO implements Serializable {

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

    //TODO pictures
}
