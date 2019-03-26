package bme.cateringunitmonitor.api.dto;

import bme.cateringunitmonitor.api.dao.CateringUnit;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class CateringUnitsResponse implements Serializable {
    private List<CateringUnit> cateringUnits;
}
