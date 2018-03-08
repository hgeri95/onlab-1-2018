package bme.cateringunitmonitor.entities.cateringunit.entity;

import bme.cateringunitmonitor.entities.BaseEntity;
import bme.cateringunitmonitor.entities.cateringunit.entity.category.CategoryParameters;
import bme.cateringunitmonitor.entities.cateringunit.entity.opening.OpeningHours;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class CateringUnit extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String description;

    private OpeningHours openingHours;

    private String address;

    private CategoryParameters categoryParameters;

    //TODO pictures
}
