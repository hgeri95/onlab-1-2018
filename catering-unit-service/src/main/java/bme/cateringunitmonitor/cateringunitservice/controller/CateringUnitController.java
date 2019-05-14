package bme.cateringunitmonitor.cateringunitservice.controller;

import bme.cateringunitmonitor.api.dto.CateringUnitDTO;
import bme.cateringunitmonitor.api.dto.CateringUnitsResponse;
import bme.cateringunitmonitor.api.exception.CateringUnitHttpException;
import bme.cateringunitmonitor.api.exception.CateringUnitServiceException;
import bme.cateringunitmonitor.api.remoting.controller.ICateringUnitController;
import bme.cateringunitmonitor.api.remoting.service.ICateringUnitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CateringUnitController implements ICateringUnitController {

    private static final Logger logger = LoggerFactory.getLogger(CateringUnitController.class);

    @Autowired
    private ICateringUnitService cateringUnitService;

    @Override
    public CateringUnitsResponse getAll() {
        logger.debug("Get all catering unit.");
        return new CateringUnitsResponse(cateringUnitService.getAll());
    }

    @Override
    public ResponseEntity delete(Long id) {
        try {
            cateringUnitService.delete(id);
            return ResponseEntity.ok().build();
        } catch (CateringUnitServiceException ex) {
            logger.error("Error in catering unit delete: {}", ex);
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @Override
    public ResponseEntity<CateringUnitDTO> create(CateringUnitDTO cateringUnitRequest) {
        try {
            CateringUnitDTO cateringUnit = cateringUnitService.create(cateringUnitRequest);
            return ResponseEntity.ok(cateringUnit);
        } catch (CateringUnitServiceException ex) {
            logger.error("Error in catering unit creation: {}", ex);
            throw new CateringUnitHttpException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @Override
    public ResponseEntity<CateringUnitDTO> update(Long id, CateringUnitDTO cateringUnitRequest) {
        try {
            CateringUnitDTO cateringUnit = cateringUnitService.update(id, cateringUnitRequest);
            return ResponseEntity.ok(cateringUnit);
        } catch (CateringUnitServiceException ex) {
            logger.error("Error in catering unit update: {}", ex);
            throw new CateringUnitHttpException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }
}
