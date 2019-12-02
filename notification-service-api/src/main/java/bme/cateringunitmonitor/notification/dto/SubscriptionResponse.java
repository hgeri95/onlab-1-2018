package bme.cateringunitmonitor.notification.dto;

import lombok.*;

import java.io.Serializable;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class SubscriptionResponse implements Serializable {
    private String username;
    private String cateringUnitName;
}
