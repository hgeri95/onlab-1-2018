package bme.cateringunitmonitor.api.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CateringUnitsResponse implements Serializable {
    private List<CateringUnitDTO> cateringUnits;
}
