package bme.cateringunitmonitor.cateringunitservice.controller;

import bme.cateringunitmonitor.cateringunitservice.service.CateringUnitService;
import bme.cateringunitmonitor.entities.cateringunit.api.CateringUnitRequest;
import bme.cateringunitmonitor.entities.cateringunit.api.CateringUnitsResponse;
import bme.cateringunitmonitor.entities.user.entity.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cateringunit")
public class CateringUnitController {

    private static final Logger logger = LoggerFactory.getLogger(CateringUnitController.class);

    @Autowired
    private CateringUnitService cateringUnitService;

    @GetMapping("/getall")
    @Secured(Role.Values.ROLE_USER)
    public CateringUnitsResponse getAll() {
        return new CateringUnitsResponse(cateringUnitService.getAll());
    }

    //Search

    @PostMapping("/create")
    @Secured(Role.Values.ROLE_OWNER)
    public ResponseEntity create(@RequestBody CateringUnitRequest cateringUnitRequest) {
        boolean success = cateringUnitService.create(cateringUnitRequest);
        return success ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/update")
    @Secured(Role.Values.ROLE_OWNER)
    public ResponseEntity update(@RequestBody CateringUnitRequest cateringUnitRequest) {
        boolean success = cateringUnitService.update(cateringUnitRequest);
        return success ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //TODO QUESTIONS FOR CONSULTANCY:
    //Itt is a Security filtert kéne használni. (?)
    //Spring remoting a rest hívásokhoz(?) ha kell egyáltalán
    //Swaggerben hogy látszhatna minden..?
    //UI
}
