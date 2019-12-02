package bme.cateringunitmonitor.rating.dao;

import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RatingDAO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cateringUnitName;

    @Column(nullable = false)
    private String username;

    private int rate;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;

    private String comment;

    public RatingDAO(String cateringUnitName, String username, int rate, String comment) {
        this.cateringUnitName = cateringUnitName;
        this.username = username;
        this.rate = rate;
        this.comment = comment;
    }
}
