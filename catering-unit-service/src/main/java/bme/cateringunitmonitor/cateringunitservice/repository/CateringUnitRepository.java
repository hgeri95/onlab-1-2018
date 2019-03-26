package bme.cateringunitmonitor.cateringunitservice.repository;

import bme.cateringunitmonitor.api.dao.CateringUnit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CateringUnitRepository extends JpaRepository<CateringUnit, Long> {

    boolean existsByName(String name);
    void deleteByName(String name);
}
