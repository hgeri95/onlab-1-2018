package bme.cateringunitmonitor.statisticservice.repository;

import bme.cateringunitmonitor.statisticservice.dao.StatisticDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StatisticRepository extends JpaRepository<StatisticDAO, Long> {

    public Optional<StatisticDAO> findByUsernameAndAndCateringUnitIdAndAndType(String username, Long cateringUnitId, String type);
    public List<StatisticDAO> findAllByCateringUnitId(Long caterinUnitId);
}
