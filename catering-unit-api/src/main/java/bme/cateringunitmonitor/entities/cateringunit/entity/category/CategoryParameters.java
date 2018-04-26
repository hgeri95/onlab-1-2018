package bme.cateringunitmonitor.entities.cateringunit.entity.category;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public class CategoryParameters implements Serializable {

    private Map<String, String> parameters;

    public CategoryParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryParameters)) return false;
        CategoryParameters that = (CategoryParameters) o;
        return Objects.equals(parameters, that.parameters);
    }

    @Override
    public int hashCode() {

        return Objects.hash(parameters);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CategoryParameters{");
        sb.append("parameters=").append(parameters);
        sb.append('}');
        return sb.toString();
    }
}
