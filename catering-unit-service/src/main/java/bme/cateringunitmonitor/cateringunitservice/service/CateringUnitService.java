package bme.cateringunitmonitor.cateringunitservice.service;

import bme.cateringunitmonitor.api.dto.CateringUnitDTO;
import bme.cateringunitmonitor.api.dto.CateringUnitRequest;
import bme.cateringunitmonitor.api.exception.CateringUnitServiceException;
import bme.cateringunitmonitor.cateringunitservice.dao.CateringUnitDAO;
import bme.cateringunitmonitor.cateringunitservice.repository.CateringUnitRepository;
import bme.cateringunitmonitor.cateringunitservice.util.CateringUnitConverter;
import lombok.extern.flogger.Flogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class CateringUnitService {

    @Autowired
    private CateringUnitRepository cateringUnitRepository;

    @Autowired
    private CateringUnitConverter cateringUnitConverter;

    public List<CateringUnitDTO> getAll() {
        return cateringUnitRepository.findAll().stream()
                .map(c -> cateringUnitConverter.convertToDTO(c))
                .collect(Collectors.toList());
    }

    public CateringUnitDTO create(CateringUnitRequest cateringUnitRequest) throws CateringUnitServiceException {
        if (cateringUnitRepository.existsByName(cateringUnitRequest.getName())) {
            throw new CateringUnitServiceException("Catering unit already exists");
        }

        CateringUnitDAO cateringUnit = cateringUnitConverter.convertToEntity(cateringUnitRequest);
        return cateringUnitConverter.convertToDTO(cateringUnitRepository.save(cateringUnit));
    }

    public CateringUnitDTO update(Long id, CateringUnitRequest cateringUnitRequest) throws CateringUnitServiceException {
        log.debug("Catering unit to update: {}, with id: {}", cateringUnitRequest, id);
        Optional<CateringUnitDAO> cateringUnitFromDb = cateringUnitRepository.findById(id);
        if (cateringUnitFromDb.isPresent()) {
            if (!cateringUnitFromDb.get().getName().equals(cateringUnitRequest.getName())) {
                throw new CateringUnitServiceException("Catering unit not changeable!");
            }
            CateringUnitDAO updatedCateringUnit = cateringUnitConverter.convertToEntity(cateringUnitRequest);
            updatedCateringUnit.setId(cateringUnitFromDb.get().getId());
            CateringUnitDAO savedCateringUnit = cateringUnitRepository.save(updatedCateringUnit);
            log.debug("Catering unit updated with id: {}", id);
            return cateringUnitConverter.convertToDTO(savedCateringUnit);
        } else {
            CateringUnitDAO cateringUnitDAO = cateringUnitConverter.convertToEntity(cateringUnitRequest);
            log.debug("Catering unit created with id: {}", cateringUnitDAO.getId());
            return cateringUnitConverter.convertToDTO(cateringUnitRepository.save(cateringUnitDAO));
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

    public CateringUnitDTO get(Long id) throws CateringUnitServiceException {
        try {
            CateringUnitDAO cateringUnitDAO = cateringUnitRepository.getOne(id);
            return cateringUnitConverter.convertToDTO(cateringUnitDAO);
        } catch (EntityNotFoundException ex) {
            log.warn("Entity not found: {}", ex);
            throw new CateringUnitServiceException(ex.getMessage());
        }
    }

    public boolean isCateringUnitExists(String cateringUnitName) {
        return cateringUnitRepository.existsByName(cateringUnitName);
    }
}
