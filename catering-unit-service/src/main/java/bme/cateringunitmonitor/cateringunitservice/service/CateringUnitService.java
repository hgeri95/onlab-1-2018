package bme.cateringunitmonitor.cateringunitservice.service;

import bme.cateringunitmonitor.api.dto.CateringUnitDTO;
import bme.cateringunitmonitor.api.exception.CateringUnitHttpException;
import bme.cateringunitmonitor.api.exception.CateringUnitServiceException;
import bme.cateringunitmonitor.api.remoting.service.ICateringUnitService;
import bme.cateringunitmonitor.cateringunitservice.dao.CateringUnitDAO;
import bme.cateringunitmonitor.cateringunitservice.repository.CateringUnitRepository;
import bme.cateringunitmonitor.cateringunitservice.util.CateringUnitConverter;
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
public class CateringUnitService implements ICateringUnitService {

    @Autowired
    private CateringUnitRepository cateringUnitRepository;

    @Autowired
    private CateringUnitConverter cateringUnitConverter;

    public List<CateringUnitDTO> getAll() {
        return cateringUnitRepository.findAll().stream()
                .map(c -> cateringUnitConverter.convertToDTO(c))
                .collect(Collectors.toList());
    }

    public CateringUnitDTO create(CateringUnitDTO cateringUnitDTO) throws CateringUnitServiceException {
        //TODO Validate datas
        if (cateringUnitRepository.existsByName(cateringUnitDTO.getName())) {
            throw new CateringUnitServiceException("Catering unit already exists");
        }

        CateringUnitDAO cateringUnit = cateringUnitConverter.convertToEntity(cateringUnitDTO);
        return cateringUnitConverter.convertToDTO(cateringUnitRepository.save(cateringUnit));
    }

    @Transactional
    public CateringUnitDTO update(Long id, CateringUnitDTO cateringUnitDTO) throws CateringUnitServiceException {
        log.debug("Catering unit to update: {}, with id: {}", cateringUnitDTO, id);
        Optional<CateringUnitDAO> cateringUnitFromDb = cateringUnitRepository.findById(id);
        if (cateringUnitFromDb.isPresent()) {
            CateringUnitDAO cateringUnitToUpdate = cateringUnitFromDb.get();
            cateringUnitToUpdate.setAddress(cateringUnitDTO.getAddress());
            cateringUnitToUpdate.setCategoryParameters(cateringUnitDTO.getCategoryParameters());
            cateringUnitToUpdate.setDescription(cateringUnitDTO.getDescription());
            cateringUnitToUpdate.setName(cateringUnitDTO.getName());
            cateringUnitToUpdate.setOpeningHours(cateringUnitDTO.getOpeningHours());

            CateringUnitDAO savedCateringUnit = cateringUnitRepository.save(cateringUnitToUpdate);
            return cateringUnitConverter.convertToDTO(savedCateringUnit);
        } else {
            CateringUnitDAO cateringUnitDAO = cateringUnitConverter.convertToEntity(cateringUnitDTO);
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

    @Override
    public CateringUnitDTO get(Long id) throws CateringUnitServiceException {
        CateringUnitDAO cateringUnitDAO;
        try {
            cateringUnitDAO = cateringUnitRepository.getOne(id);
        } catch (EntityNotFoundException ex) {
            log.debug("Entity not found: {}", ex);
            throw new CateringUnitServiceException(ex.getMessage());
        }
        return cateringUnitConverter.convertToDTO(cateringUnitDAO);
    }
}
