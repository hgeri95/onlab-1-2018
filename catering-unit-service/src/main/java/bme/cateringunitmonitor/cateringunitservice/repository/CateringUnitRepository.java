package bme.cateringunitmonitor.cateringunitservice.repository;

import bme.cateringunitmonitor.api.dao.CateringUnitDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CateringUnitRepository extends JpaRepository<CateringUnitDAO, Long> {

    boolean existsByName(String name);
    void deleteByName(String name);
}
