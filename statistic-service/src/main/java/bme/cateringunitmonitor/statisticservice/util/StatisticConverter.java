package bme.cateringunitmonitor.statisticservice.util;

import bme.cateringunitmonitor.api.dto.StatisticDTO;
import bme.cateringunitmonitor.statisticservice.dao.StatisticDAO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StatisticConverter {
    @Autowired
    private ModelMapper mapper;

    public StatisticDTO convertToDTO(StatisticDAO statisticDAO) {
        return mapper.map(statisticDAO, StatisticDTO.class);
    }
}
