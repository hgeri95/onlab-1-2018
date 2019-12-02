package bme.cateringunitmonitor.notification.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class NotificationSubscribedRequest implements Serializable {
    @NotBlank
    private String subject;
    @NotBlank
    private String message;
}
