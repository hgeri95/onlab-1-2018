package bme.cateringunitmonitor.api.remoting.controller;

import bme.cateringunitmonitor.api.Role;
import bme.cateringunitmonitor.api.dto.StatisticDTO;
import bme.cateringunitmonitor.api.dto.StatisticValuesDTO;
import bme.cateringunitmonitor.feign.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "StatisticController", url = "${sttisticServiceUrl}", configuration = FeignConfiguration.class)
public interface IStatisticController {
    public static final String BASE_PATH = "api/v1/statistic";

    @GetMapping(BASE_PATH + "/{cateringUnitId}")
    @Secured({Role.Values.ROLE_USER, Role.Values.ROLE_ADMIN})
    public List<StatisticValuesDTO> getStatisticForCateringUnit(@PathVariable("cateringUnitId") Long cateringUnitId);

    @PostMapping(BASE_PATH + "/{type}/{cateringUnitId}")
    @Secured({Role.Values.ROLE_USER, Role.Values.ROLE_ADMIN})
    public ResponseEntity<StatisticDTO> addStatisticToCateringUnit(@PathVariable("cateringUnitId") Long cateringUnitId,
                                                                   @PathVariable("type") String type);
}
