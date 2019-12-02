package bme.cateringunitmonitor.cateringunitservice.repository;

import bme.cateringunitmonitor.cateringunitservice.dao.CateringUnitDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CateringUnitRepository extends JpaRepository<CateringUnitDAO, Long> {

    List<CateringUnitDAO> findAllByOwner(String owner);
    boolean existsByName(String name);
}
