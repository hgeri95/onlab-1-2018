package bme.cateringunitmonitor.cateringunitservice.dao;

import bme.cateringunitmonitor.api.OpeningPerDay;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Indexed
public class CateringUnitDAO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Field
    private String name;

    @Field
    private String description;

    private String owner;

    @ElementCollection(fetch = FetchType.EAGER)
    @Lob
    @Column(length = 100000)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<OpeningPerDay> openingHours = new ArrayList<>();

    @Lob
    @Column(length = 100000)
    @IndexedEmbedded
    private Address address;

    @ElementCollection(fetch = FetchType.EAGER)
    @Lob
    @Column(length = 100000)
    @Fetch(value = FetchMode.SUBSELECT)
    @IndexedEmbedded
    private List<CategoryParameter> categoryParameters = new ArrayList<>();
}
