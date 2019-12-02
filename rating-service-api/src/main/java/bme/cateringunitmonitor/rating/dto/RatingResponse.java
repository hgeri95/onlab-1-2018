package bme.cateringunitmonitor.rating.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class RatingResponse implements Serializable {
    private String username;
    private String cateringUnitName;
    private int rate;
    private String comment;
}
