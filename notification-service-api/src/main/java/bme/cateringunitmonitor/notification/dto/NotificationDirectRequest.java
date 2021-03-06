package bme.cateringunitmonitor.notification.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class NotificationDirectRequest implements Serializable {
    @Email
    private String email;
    @NotBlank
    private String subject;
    @NotBlank
    private String message;
}
