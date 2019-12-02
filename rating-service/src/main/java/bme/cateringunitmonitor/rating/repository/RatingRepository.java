package bme.cateringunitmonitor.rating.repository;

import bme.cateringunitmonitor.rating.dao.RatingDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<RatingDAO, Long> {

    Optional<RatingDAO> findByUsernameAndCateringUnitName(String username, String cateringUnitName);
    List<RatingDAO> findAllByCateringUnitName(String cateringUnitName);
    List<RatingDAO> findAllByUsername(String username);
    int deleteAllByCateringUnitName(String cateringUnitName);
    int deleteAllByUsername(String username);
    List<RatingDAO> findByCateringUnitNameIn(List<String> cateringUnitNames);
}
