package bme.cateringunitmonitor.cateringunitservice.service;

import bme.cateringunitmonitor.api.dao.CateringUnitDAO;
import bme.cateringunitmonitor.api.dto.CateringUnitRequest;
import bme.cateringunitmonitor.api.exception.CateringUnitServiceException;
import bme.cateringunitmonitor.api.remoting.service.ICateringUnitService;
import bme.cateringunitmonitor.cateringunitservice.repository.CateringUnitRepository;
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

    public List<CateringUnitDAO> getAll() {
        return cateringUnitRepository.findAll();
    }

    public CateringUnitDAO create(CateringUnitRequest cateringUnitRequest) throws CateringUnitServiceException {
        //TODO Validate datas
        if (cateringUnitRepository.existsByName(cateringUnitRequest.getName())) {
            throw new CateringUnitServiceException("Catering unit already exists");
        }

        CateringUnitDAO cateringUnit = new CateringUnitDAO(
                cateringUnitRequest.getName(),
                cateringUnitRequest.getDescription(),
                cateringUnitRequest.getOpeningHours(),
                cateringUnitRequest.getAddress(),
                cateringUnitRequest.getCategoryParameters()
        );

        return cateringUnitRepository.save(cateringUnit);
    }

    public CateringUnitDAO update(Long id, CateringUnitRequest cateringUnitRequest) throws CateringUnitServiceException {
        log.debug("Catering unit to update: {}, with id: {}", cateringUnitRequest, id);
        Optional<CateringUnitDAO> cateringUnitFromDb = cateringUnitRepository.findById(id);
        if (cateringUnitFromDb.isPresent()) {
            CateringUnitDAO cateringUnitToUpdate = cateringUnitFromDb.get();
            cateringUnitToUpdate.setAddress(cateringUnitRequest.getAddress());
            cateringUnitToUpdate.setCategoryParameters(cateringUnitRequest.getCategoryParameters());
            cateringUnitToUpdate.setDescription(cateringUnitRequest.getDescription());
            cateringUnitToUpdate.setName(cateringUnitRequest.getName());
            cateringUnitToUpdate.setOpeningHours(cateringUnitRequest.getOpeningHours());

            return cateringUnitRepository.save(cateringUnitToUpdate);
        } else {
            CateringUnitDAO cateringUnit = new CateringUnitDAO(
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
