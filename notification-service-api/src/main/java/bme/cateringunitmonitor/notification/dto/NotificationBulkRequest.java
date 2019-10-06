package bme.cateringunitmonitor.notification.dto;


import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class NotificationBulkRequest implements Serializable {
    @NotNull
    private List<String> usernames;
    @NotBlank
    private String subject;
    @NotBlank
    private String message;
}
