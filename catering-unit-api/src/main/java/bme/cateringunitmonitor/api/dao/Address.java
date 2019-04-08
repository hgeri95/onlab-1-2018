package bme.cateringunitmonitor.api.dao;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class Address implements Serializable {
    private String address;
    private Coordinate coordinate;
    private String otherInformation;
}
