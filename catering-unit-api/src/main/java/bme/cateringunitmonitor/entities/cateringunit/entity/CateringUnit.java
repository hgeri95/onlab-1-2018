package bme.cateringunitmonitor.entities.cateringunit.entity;

import bme.cateringunitmonitor.entities.BaseEntity;
import bme.cateringunitmonitor.entities.cateringunit.entity.category.CategoryParameters;
import bme.cateringunitmonitor.entities.cateringunit.entity.opening.OpeningHours;

import javax.persistence.Entity;

@Entity
public class CateringUnit extends BaseEntity {

    private String name;

    private String description;

    private OpeningHours openingHours;

    private String address;

    private CategoryParameters categoryParameters;

    //TODO pictures
}
