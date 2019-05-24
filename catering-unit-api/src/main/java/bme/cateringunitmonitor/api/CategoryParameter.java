package bme.cateringunitmonitor.api;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CategoryParameter implements Serializable {

    @NotBlank
    private String key;
    @NotBlank
    private String value;
}
