package bme.cateringunitmonitor.cateringunitservice.service;

import bme.cateringunitmonitor.cateringunitservice.repository.CateringUnitRepository;
import bme.cateringunitmonitor.api.dto.CateringUnitRequest;
import bme.cateringunitmonitor.api.dao.CateringUnit;
import bme.cateringunitmonitor.api.exception.CateringUnitServiceException;
import bme.cateringunitmonitor.api.remoting.service.ICateringUnitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
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

    public CateringUnit update(Long id, CateringUnitRequest cateringUnitRequest) throws CateringUnitServiceException {
        log.debug("Catering unit to update: {}, with id: {}", cateringUnitRequest, id);
        Optional<CateringUnit> cateringUnitFromDb = cateringUnitRepository.findById(id);
        if (cateringUnitFromDb.isPresent()) {
            CateringUnit cateringUnitToUpdate = cateringUnitFromDb.get();
            cateringUnitToUpdate.setAddress(cateringUnitRequest.getAddress());
            cateringUnitToUpdate.setCategoryParameters(cateringUnitRequest.getCategoryParameters());
            cateringUnitToUpdate.setDescription(cateringUnitRequest.getDescription());
            cateringUnitToUpdate.setName(cateringUnitRequest.getName());
            cateringUnitToUpdate.setOpeningHours(cateringUnitRequest.getOpeningHours());

            return cateringUnitRepository.save(cateringUnitToUpdate);
        } else {
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

    public void delete(Long id) throws CateringUnitServiceException {
        try {
            cateringUnitRepository.deleteById(id);
        } catch (IllegalArgumentException ex) {
            log.error("Failed to delete Catering unit with id: {}, exception: {}", id, ex);
            throw new CateringUnitServiceException(ex.getMessage());
        }
    }
}
