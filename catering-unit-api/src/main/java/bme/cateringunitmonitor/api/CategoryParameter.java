package bme.cateringunitmonitor.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class CategoryParameter implements Serializable {

    private String key;
    private String value;
}
