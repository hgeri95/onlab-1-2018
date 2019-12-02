package bme.cateringunitmonitor.notification.dao;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SubscriptionDAO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cateringUnitName;

    @Column(nullable = false)
    private String username;

    public SubscriptionDAO(String cateringUnitName, String username) {
        this.cateringUnitName = cateringUnitName;
        this.username = username;
    }
}
