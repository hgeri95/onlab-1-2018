package bme.cateringunitmonitor.cateringunitservice.service;

import bme.cateringunitmonitor.api.Address;
import bme.cateringunitmonitor.api.Coordinate;
import bme.cateringunitmonitor.api.dto.CateringUnitDTO;
import bme.cateringunitmonitor.api.dto.CateringUnitRequest;
import bme.cateringunitmonitor.api.dto.CateringUnitWithDistanceDTO;
import bme.cateringunitmonitor.api.dto.CateringUnitsResponse;
import bme.cateringunitmonitor.api.exception.CateringUnitServiceException;
import bme.cateringunitmonitor.cateringunitservice.dao.CateringUnitDAO;
import bme.cateringunitmonitor.cateringunitservice.repository.CateringUnitRepository;
import bme.cateringunitmonitor.cateringunitservice.util.CateringUnitConverter;
import bme.cateringunitmonitor.utils.amqp.GenericEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static bme.cateringunitmonitor.utils.amqp.EventTypes.DELETE_CATERING_EVENT;

@Service
@Transactional
@Slf4j
public class CateringUnitService {

    @Autowired
    private CateringUnitRepository cateringUnitRepository;

    @Autowired
    private CateringUnitConverter cateringUnitConverter;

    @Autowired(required = false)//Optional because of testing only
    private Optional<EventSender> eventSender;

    public List<CateringUnitDTO> getAll() {
        return cateringUnitRepository.findAll().stream()
                .map(c -> cateringUnitConverter.convertToDTO(c))
                .sorted(Comparator.comparing(CateringUnitDTO::getName))
                .collect(Collectors.toList());
    }

    public CateringUnitDTO create(CateringUnitRequest cateringUnitRequest, String ownerName) throws CateringUnitServiceException {
        if (cateringUnitRepository.existsByName(cateringUnitRequest.getName())) {
            throw new CateringUnitServiceException("Catering unit already exists");
        }

        CateringUnitDAO cateringUnit = cateringUnitConverter.convertToEntity(cateringUnitRequest);
        cateringUnit.setOwner(ownerName);
        return cateringUnitConverter.convertToDTO(cateringUnitRepository.save(cateringUnit));
    }

    public CateringUnitDTO update(Long id, CateringUnitRequest cateringUnitRequest, String ownerName) throws CateringUnitServiceException {
        log.debug("Catering unit to update: {}, with id: {}", cateringUnitRequest, id);
        Optional<CateringUnitDAO> cateringUnitFromDb = cateringUnitRepository.findById(id);
        if (cateringUnitFromDb.isPresent()) {
            if (!cateringUnitFromDb.get().getName().equals(cateringUnitRequest.getName())) {
                throw new CateringUnitServiceException("Catering unit not changeable!");
            }
            CateringUnitDAO updatedCateringUnit = cateringUnitConverter.convertToEntity(cateringUnitRequest);
            updatedCateringUnit.setId(cateringUnitFromDb.get().getId());
            updatedCateringUnit.setOwner(ownerName);
            CateringUnitDAO savedCateringUnit = cateringUnitRepository.save(updatedCateringUnit);
            log.debug("Catering unit updated with id: {}", id);
            return cateringUnitConverter.convertToDTO(savedCateringUnit);
        } else {
            //TODO think through
            CateringUnitDAO cateringUnitDAO = cateringUnitConverter.convertToEntity(cateringUnitRequest);
            cateringUnitDAO.setOwner(ownerName);
            log.debug("Catering unit created with id: {}", cateringUnitDAO.getId());
            return cateringUnitConverter.convertToDTO(cateringUnitRepository.save(cateringUnitDAO));
        }
    }

    public void delete(Long id) throws CateringUnitServiceException {
        Optional<CateringUnitDAO> cateringUnitToDelete = cateringUnitRepository.findById(id);
        try {
            if (cateringUnitToDelete.isPresent()) {
                final String cateringUnitName = cateringUnitToDelete.get().getName();
                cateringUnitRepository.deleteById(id);
                sendDeleteCateringEvent(cateringUnitName);
            } else {
                throw new CateringUnitServiceException("Catering unit not exists with id: " + id);
            }
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

    public CateringUnitsResponse getAllCateringUnitForOwner(String owner) {
        log.debug("Get all catering units for owner: {}", owner);
        List<CateringUnitDAO> cateringUnitsFromDb = cateringUnitRepository.findAllByOwner(owner);
        List<CateringUnitDTO> cateringUnits = cateringUnitsFromDb.stream()
                .map(c -> cateringUnitConverter.convertToDTO(c)).collect(Collectors.toList());
        CateringUnitsResponse response = new CateringUnitsResponse(cateringUnits);
        return response;
    }

    public List<CateringUnitWithDistanceDTO> nearestCaterings(double latitude, double longitude, double distanceInKm) {
        log.info("Get nearest catering with latitude: {}, longitude: {}, distance: {}", latitude, longitude, distanceInKm);
        Coordinate userLocation = new Coordinate(latitude, longitude);
        List<CateringUnitDAO> cateringsInTolerance = cateringUnitRepository.findAll()
                .stream().filter(c -> cateringCoordinateIsInTolerance(c, latitude, longitude)).collect(Collectors.toList());

        List<CateringUnitWithDistanceDTO> caterings = cateringsInTolerance.stream()
                .map(c -> new CateringUnitWithDistanceDTO(
                        c.getId(), c.getName(),
                        new Address(c.getAddress().getAddress(), c.getAddress().getCoordinate(), c.getAddress().getOtherInformation()),
                        c.getAddress().getCoordinate().distanceTo(userLocation))).collect(Collectors.toList());
        log.debug("Caterings: {}", caterings);
        List<CateringUnitWithDistanceDTO> nearestCaterings = caterings.stream().filter(c -> c.getDistance() <= distanceInKm)
                .sorted(Comparator.comparingDouble(CateringUnitWithDistanceDTO::getDistance)).collect(Collectors.toList());

        log.debug("Nearest caterings result: {}", nearestCaterings);
        return nearestCaterings;
    }

    private boolean cateringCoordinateIsInTolerance(CateringUnitDAO catering, double latitude, double longitude) {
        double cateringLatitude = catering.getAddress().getCoordinate().getLatitude();
        boolean isLatitudeOk = Math.abs(cateringLatitude - latitude) < 5.0;
        double cateringLongitude = catering.getAddress().getCoordinate().getLongitude();
        boolean isLongitudeOk = Math.abs(cateringLongitude- longitude) < 5.0;

        return isLatitudeOk && isLongitudeOk;
    }

    private void sendDeleteCateringEvent(String cateringUnitName) {
        if (eventSender.isPresent()) {
            GenericEvent cateringUnitDeleteEvent = new GenericEvent(DELETE_CATERING_EVENT,
                    "cateringUnitName", cateringUnitName);
            eventSender.get().send(cateringUnitDeleteEvent.getMessage());
        } else {
            log.error("Event sender is missing!");
        }
    }
}
