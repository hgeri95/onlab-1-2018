package bme.cateringunitmonitor.api.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UserInfoBulkRequest implements Serializable {
    private List<String> usernames;
}
