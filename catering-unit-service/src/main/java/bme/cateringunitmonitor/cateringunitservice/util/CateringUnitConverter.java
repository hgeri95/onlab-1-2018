package bme.cateringunitmonitor.cateringunitservice.util;

import bme.cateringunitmonitor.api.dto.CateringUnitDTO;
import bme.cateringunitmonitor.api.dto.CateringUnitRequest;
import bme.cateringunitmonitor.cateringunitservice.dao.CateringUnitDAO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CateringUnitConverter {

    @Autowired
    private ModelMapper mapper;

    public CateringUnitDAO convertToEntity(CateringUnitDTO cateringUnitDTO) {
        return mapper.map(cateringUnitDTO, CateringUnitDAO.class);
    }

    public CateringUnitDAO convertToEntity(CateringUnitRequest cateringUnitRequest) {
        return mapper.map(cateringUnitRequest, CateringUnitDAO.class);
    }

    public CateringUnitDTO convertToDTO(CateringUnitDAO cateringUnitDAO) {
        return mapper.map(cateringUnitDAO, CateringUnitDTO.class);
    }
}
