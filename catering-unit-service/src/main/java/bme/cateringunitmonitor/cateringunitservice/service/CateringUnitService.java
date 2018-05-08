package bme.cateringunitmonitor.cateringunitservice.service;

import bme.cateringunitmonitor.cateringunitservice.repository.CateringUnitRepository;
import bme.cateringunitmonitor.entities.cateringunit.api.CateringUnitRequest;
import bme.cateringunitmonitor.entities.cateringunit.entity.CateringUnit;
import bme.cateringunitmonitor.entities.exception.CateringUnitServiceException;
import bme.cateringunitmonitor.remoting.service.ICateringUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CateringUnitService implements ICateringUnitService {

    @Autowired
    private CateringUnitRepository cateringUnitRepository;

    public List<CateringUnit> getAll() {
        return cateringUnitRepository.findAll();
    }

    public CateringUnit create(CateringUnitRequest cateringUnitRequest) throws CateringUnitServiceException {
        //TODO Validate datas
        if (cateringUnitRepository.existsByName(cateringUnitRequest.getName())) {
            throw new CateringUnitServiceException("Catering unit already exists");
        }

        CateringUnit cateringUnit = new CateringUnit(
                cateringUnitRequest.getName(),
                cateringUnitRequest.getDescription(),
                cateringUnitRequest.getOpeningHours(),
                cateringUnitRequest.getAddress(),
                cateringUnitRequest.getCategoryParameters()
        );

        return cateringUnitRepository.save(cateringUnit);
    }

    public CateringUnit update(CateringUnitRequest cateringUnitRequest) throws CateringUnitServiceException {
        if (!cateringUnitRepository.existsByName(cateringUnitRequest.getName())) {
            throw new CateringUnitServiceException("Catering unit does not exists");
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

        return cateringUnitRepository.save(cateringUnit);
    }
}
