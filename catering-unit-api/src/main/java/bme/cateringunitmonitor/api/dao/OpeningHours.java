package bme.cateringunitmonitor.api.dao;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class OpeningHours implements Serializable {
    private Map<String, OpeningPerDay> opening = new HashMap<String, OpeningPerDay>(7);

    public OpeningHours(Map<String, OpeningPerDay> opening) {
        this.opening = opening;
    }
}
