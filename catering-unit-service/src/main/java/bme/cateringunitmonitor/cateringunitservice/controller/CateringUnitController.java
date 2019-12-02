package bme.cateringunitmonitor.cateringunitservice.controller;

import bme.cateringunitmonitor.api.dto.CateringUnitDTO;
import bme.cateringunitmonitor.api.dto.CateringUnitRequest;
import bme.cateringunitmonitor.api.dto.CateringUnitWithDistanceDTO;
import bme.cateringunitmonitor.api.dto.CateringUnitsResponse;
import bme.cateringunitmonitor.api.exception.CateringUnitHttpException;
import bme.cateringunitmonitor.api.exception.CateringUnitServiceException;
import bme.cateringunitmonitor.api.remoting.controller.ICateringUnitController;
import bme.cateringunitmonitor.cateringunitservice.service.CateringUnitService;
import bme.cateringunitmonitor.cateringunitservice.service.SearchService;
import bme.cateringunitmonitor.security.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CateringUnitController implements ICateringUnitController {

    private static final Logger logger = LoggerFactory.getLogger(CateringUnitController.class);

    @Autowired
    private CateringUnitService cateringUnitService;

    @Autowired
    private SearchService searchService;

    @Override
    public CateringUnitsResponse getAll() {
        logger.debug("Get all catering units.");
        return new CateringUnitsResponse(cateringUnitService.getAll());
    }

    @Override
    public CateringUnitsResponse getOwned() {
        String username = SecurityUtil.getActiveUser();
        logger.debug("Get owned catering units for user: {}", username);
        return cateringUnitService.getAllCateringUnitForOwner(username);
    }

    @Override
    public ResponseEntity delete(Long id) {
        try {
            cateringUnitService.delete(id);
            return ResponseEntity.ok().build();
        } catch (CateringUnitServiceException ex) {
            logger.warn("Error in catering unit delete: {}", ex);
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @Override
    public ResponseEntity<CateringUnitDTO> create(@Valid CateringUnitRequest cateringUnitRequest) {
        try {
            String ownerName = SecurityUtil.getActiveUser();
            return ResponseEntity.ok(cateringUnitService.create(cateringUnitRequest, ownerName));
        } catch (CateringUnitServiceException ex) {
            logger.warn("Error in catering unit creation: {}", ex);
            throw new CateringUnitHttpException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @Override
    public ResponseEntity<CateringUnitDTO> update(Long id, @Valid CateringUnitRequest cateringUnitRequest) {
        try {
            String ownerName = SecurityUtil.getActiveUser();
            CateringUnitDTO cateringUnit = cateringUnitService.update(id, cateringUnitRequest, ownerName);
            return ResponseEntity.ok(cateringUnit);
        } catch (CateringUnitServiceException ex) {
            logger.warn("Error in catering unit update: {}", ex);
            throw new CateringUnitHttpException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @Override
    public ResponseEntity<CateringUnitDTO> get(Long id) {
        try {
            return ResponseEntity.ok(cateringUnitService.get(id));
        } catch (CateringUnitServiceException ex) {
            logger.warn("Catering unit not found with id: {}", id);
            throw new CateringUnitHttpException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @Override
    public Boolean checkCateringUnitExists(String cateringUnitName) {
        return cateringUnitService.isCateringUnitExists(cateringUnitName);
    }

    @Override
    public List<CateringUnitDTO> search(String searchTerm) {
        return searchService.search(searchTerm);
    }

    @Override
    public List<CateringUnitWithDistanceDTO> getNearestCaterings(double distance, double latitude, double longitude) {
        return cateringUnitService.nearestCaterings(latitude, longitude, distance);
    }
}
