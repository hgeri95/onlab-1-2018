package bme.cateringunitmonitor.cateringunitservice.dao;

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
public class CategoryParameter implements Serializable {

    @NotBlank
    private String key;
    @NotBlank
    @Field
    private String value;
}
