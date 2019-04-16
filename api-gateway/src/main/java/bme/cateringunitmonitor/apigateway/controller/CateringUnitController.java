package bme.cateringunitmonitor.apigateway.controller;

import bme.cateringunitmonitor.api.Role;
import bme.cateringunitmonitor.api.dto.CateringUnitDTO;
import bme.cateringunitmonitor.api.dto.CateringUnitsResponse;
import bme.cateringunitmonitor.api.exception.CateringUnitServiceException;
import bme.cateringunitmonitor.api.remoting.controller.ICateringUnitController;
import bme.cateringunitmonitor.api.remoting.service.ICateringUnitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    //TODO Search nice to have

    @DeleteMapping("/delete/{id}")
    @Secured(Role.Values.ROLE_OWNER)
    public ResponseEntity delete(@PathVariable("id") Long id) {
        try {
            cateringUnitService.delete(id);
            return ResponseEntity.ok().build();
        } catch (CateringUnitServiceException ex) {
            logger.error("Error in catering unit delete: {}", ex);
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/create")
    @Secured(Role.Values.ROLE_OWNER)
    public ResponseEntity create(@RequestBody CateringUnitDTO cateringUnitRequest) {
        try {
            CateringUnitDTO cateringUnit = cateringUnitService.create(cateringUnitRequest);
            return ResponseEntity.ok().body(cateringUnit);
        } catch (CateringUnitServiceException ex) {
            logger.error("Error in catering unit creation: {}", ex);
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    @Secured(Role.Values.ROLE_OWNER)
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody CateringUnitDTO cateringUnitRequest) {
        try {
            CateringUnitDTO cateringUnit = cateringUnitService.update(id, cateringUnitRequest);
            return ResponseEntity.ok().body(cateringUnit);
        } catch (CateringUnitServiceException ex) {
            logger.error("Error in catering unit update: {}", ex);
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
