package bme.cateringunitmonitor.api;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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
