package bme.cateringunitmonitor.entities.cateringunit.entity.category;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class CategoryParameter implements Serializable {

    private String key;
    private String value;

    @JsonCreator
    public CategoryParameter(
            @JsonProperty("key") String key,
            @JsonProperty("value") String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryParameter)) return false;
        CategoryParameter that = (CategoryParameter) o;
        return Objects.equals(key, that.key) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {

        return Objects.hash(key, value);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CategoryParameter{");
        sb.append("key='").append(key).append('\'');
        sb.append(", value='").append(value).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
