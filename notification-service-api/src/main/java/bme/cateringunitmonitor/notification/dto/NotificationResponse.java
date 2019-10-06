package bme.cateringunitmonitor.notification.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class NotificationResponse implements Serializable {
    private List<String> receivedBy;
}
