package bme.cateringunitmonitor.statisticservice.controller;

import bme.cateringunitmonitor.api.dto.StatisticDTO;
import bme.cateringunitmonitor.api.dto.StatisticValuesDTO;
import bme.cateringunitmonitor.api.exception.StatisticServiceException;
import bme.cateringunitmonitor.api.remoting.controller.IStatisticController;
import bme.cateringunitmonitor.security.SecurityUtil;
import bme.cateringunitmonitor.statisticservice.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class StatisticController implements IStatisticController {

    @Autowired
    private StatisticService statisticService;

    @Override
    public List<StatisticValuesDTO> getStatisticForCateringUnit(Long cateringUnitId) {
        return statisticService.getStatisticForCateringUnit(cateringUnitId);
    }

    @Override
    public ResponseEntity<StatisticDTO> addStatisticToCateringUnit(Long cateringUnitId, String type) {
        String username = SecurityUtil.getActiveUser();
        try {
            return ResponseEntity.ok(statisticService.addStatistic(username, cateringUnitId, type));
        } catch (StatisticServiceException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }
}
