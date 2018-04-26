package bme.cateringunitmonitor.cateringunitservice.service;

import bme.cateringunitmonitor.cateringunitservice.repository.CateringUnitRepository;
import bme.cateringunitmonitor.entities.cateringunit.api.CateringUnitRequest;
import bme.cateringunitmonitor.entities.cateringunit.entity.CateringUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CateringUnitService {

    @Autowired
    private CateringUnitRepository cateringUnitRepository;

    public List<CateringUnit> getAll() {
        return cateringUnitRepository.findAll();
    }

    public boolean create(CateringUnitRequest cateringUnitRequest) {
        //TODO Validate datas
        if (cateringUnitRepository.existsByName(cateringUnitRequest.getName())) {
            return false;
        }

        CateringUnit cateringUnit = new CateringUnit(
                cateringUnitRequest.getName(),
                cateringUnitRequest.getDescription(),
                cateringUnitRequest.getOpeningHours(),
                cateringUnitRequest.getAddress(),
                cateringUnitRequest.getCategoryParameters()
        );
        cateringUnitRepository.save(cateringUnit);
        return true;//If not valid false
    }

    public boolean update(CateringUnitRequest cateringUnitRequest) {
        if (!cateringUnitRepository.existsByName(cateringUnitRequest.getName())) {
            return false;
        }
        //TODO validate new datas
        cateringUnitRepository.deleteByName(cateringUnitRequest.getName());
        CateringUnit cateringUnit = new CateringUnit(
                cateringUnitRequest.getName(),
                cateringUnitRequest.getDescription(),
                cateringUnitRequest.getOpeningHours(),
                cateringUnitRequest.getAddress(),
                cateringUnitRequest.getCategoryParameters()
        );
        cateringUnitRepository.save(cateringUnit);
        return true;
    }
}
