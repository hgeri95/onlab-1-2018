package bme.cateringunitmonitor.apigateway.controller;

import bme.cateringunitmonitor.entities.cateringunit.api.CateringUnitRequest;
import bme.cateringunitmonitor.entities.cateringunit.api.CateringUnitsResponse;
import bme.cateringunitmonitor.entities.cateringunit.entity.CateringUnit;
import bme.cateringunitmonitor.entities.exception.CateringUnitServiceException;
import bme.cateringunitmonitor.entities.user.entity.Role;
import bme.cateringunitmonitor.remoting.controller.ICateringUnitController;
import bme.cateringunitmonitor.remoting.service.ICateringUnitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cateringunit")
public class CateringUnitController implements ICateringUnitController {

    private static final Logger logger = LoggerFactory.getLogger(CateringUnitController.class);

    @Autowired
    private ICateringUnitService cateringUnitService;

    @GetMapping("/getall")
    @Secured(Role.Values.ROLE_USER)
    public CateringUnitsResponse getAll() {
        logger.debug("Get all catering unit.");
        return new CateringUnitsResponse(cateringUnitService.getAll());
    }

    //Search nice to have

    //TODO Delete

    @PostMapping("/create")
    @Secured(Role.Values.ROLE_OWNER)
    public ResponseEntity create(@RequestBody CateringUnitRequest cateringUnitRequest) {
        try {
            CateringUnit cateringUnit = cateringUnitService.create(cateringUnitRequest);
            return ResponseEntity.ok().body(cateringUnit);
        } catch (CateringUnitServiceException ex) {
            logger.error("Error in catering unit creation: {}", ex);
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/update")
    @Secured(Role.Values.ROLE_OWNER)
    public ResponseEntity update(@RequestBody CateringUnitRequest cateringUnitRequest) {
        try {
            CateringUnit cateringUnit = cateringUnitService.update(cateringUnitRequest);
            return ResponseEntity.ok().body(cateringUnit);
        } catch (CateringUnitServiceException ex) {
            logger.error("Error in catering unit update: {}", ex);
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
