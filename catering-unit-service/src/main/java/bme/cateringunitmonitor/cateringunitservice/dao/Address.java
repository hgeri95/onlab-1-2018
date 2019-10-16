package bme.cateringunitmonitor.cateringunitservice.dao;

import bme.cateringunitmonitor.api.Coordinate;
import lombok.*;
import org.hibernate.search.annotations.Field;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Address implements Serializable {
    @NotBlank
    @Field
    private String address;
    private Coordinate coordinate;
    @Field
    private String otherInformation;
}
