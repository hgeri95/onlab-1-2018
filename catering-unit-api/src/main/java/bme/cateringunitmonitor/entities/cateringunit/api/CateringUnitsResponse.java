package bme.cateringunitmonitor.entities.cateringunit.api;

import bme.cateringunitmonitor.entities.cateringunit.entity.CateringUnit;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class CateringUnitsResponse implements Serializable {

    private List<CateringUnit> cateringUnits;

    public CateringUnitsResponse(List<CateringUnit> cateringUnits) {
        this.cateringUnits = cateringUnits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CateringUnitsResponse)) return false;
        CateringUnitsResponse that = (CateringUnitsResponse) o;
        return Objects.equals(cateringUnits, that.cateringUnits);
    }

    @Override
    public int hashCode() {

        return Objects.hash(cateringUnits);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CateringUnitsResponse{");
        sb.append("cateringUnits=").append(cateringUnits);
        sb.append('}');
        return sb.toString();
    }
}
