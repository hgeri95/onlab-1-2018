package bme.cateringunitmonitor.statisticservice.dao;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class StatisticDAO  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long cateringUnitId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private LocalDateTime creation;

    @Column(nullable = false)
    private String type;

    public StatisticDAO(Long cateringUnitId, String username, LocalDateTime creation, String type) {
        this.cateringUnitId = cateringUnitId;
        this.username = username;
        this.creation = creation;
        this.type = type;
    }
}
