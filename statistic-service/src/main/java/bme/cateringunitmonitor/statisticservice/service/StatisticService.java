package bme.cateringunitmonitor.statisticservice.service;

import bme.cateringunitmonitor.api.dto.StatisticDTO;
import bme.cateringunitmonitor.api.dto.StatisticValuesDTO;
import bme.cateringunitmonitor.api.exception.StatisticServiceException;
import bme.cateringunitmonitor.statisticservice.dao.StatisticDAO;
import bme.cateringunitmonitor.statisticservice.repository.StatisticRepository;
import bme.cateringunitmonitor.statisticservice.util.StatisticConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Slf4j
@Transactional
public class StatisticService {

    @Autowired
    private StatisticRepository statisticRepository;

    @Autowired
    private StatisticConverter statisticConverter;

    public List<StatisticValuesDTO> getStatisticForCateringUnit(Long cateringUnitId) {
        log.info("Get statistics for catering: {}", cateringUnitId);
        List<StatisticDAO> statistics = statisticRepository.findAllByCateringUnitId(cateringUnitId);

        Map<String, StatisticValuesDTO> groupedByTypeStatistics = new HashMap<>();
        for (StatisticDAO statistic : statistics) {
            if (groupedByTypeStatistics.containsKey(statistic.getType())) {
                groupedByTypeStatistics.get(statistic.getType()).increment();
            } else {
                StatisticValuesDTO statisticValues = new StatisticValuesDTO(
                        statistic.getType(), statistic.getCateringUnitId(), 1);
                groupedByTypeStatistics.put(statistic.getType(), statisticValues);
            }
        }

        log.debug("Statistics: {} for catering: {}", groupedByTypeStatistics, cateringUnitId);
        return new ArrayList<>(groupedByTypeStatistics.values());
    }

    public StatisticDTO addStatistic(String username, Long cateringUnitId, String type) throws StatisticServiceException {
        log.info("Add statistic for cateringUnit: {}, by user: {}, with type: {}", cateringUnitId, username, type);
        LocalDateTime now = LocalDateTime.now();
        Optional<StatisticDAO> existingStatistic = statisticRepository
                .findByUsernameAndAndCateringUnitIdAndAndType(username, cateringUnitId, type);
        if (existingStatistic.isPresent()) {
            throw new StatisticServiceException("Statistic already exists!");
        } else {
            StatisticDAO statistic = new StatisticDAO(cateringUnitId, username, now, type);

            StatisticDAO statisticDAO = statisticRepository.save(statistic);
            log.debug("Statistic added: {}", statisticDAO);
            return statisticConverter.convertToDTO(statisticDAO);
        }
    }

    @Scheduled(fixedRate = 300000)//Run in every 5 minutes and delete 2 hour long statistics
    public void clearOldStatistics() {
        List<StatisticDAO> allStatistic = statisticRepository.findAll();
        LocalDateTime now = LocalDateTime.now();
        for (StatisticDAO statistic : allStatistic) {
            if (ChronoUnit.MINUTES.between(statistic.getCreation(), now) > 120) {
                statisticRepository.delete(statistic);
            }
        }
    }
}
