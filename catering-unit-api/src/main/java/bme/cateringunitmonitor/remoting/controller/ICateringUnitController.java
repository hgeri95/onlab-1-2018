package bme.cateringunitmonitor.remoting.controller;

import bme.cateringunitmonitor.entities.cateringunit.api.CateringUnitRequest;
import bme.cateringunitmonitor.entities.cateringunit.api.CateringUnitsResponse;
import bme.cateringunitmonitor.entities.user.entity.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

public interface ICateringUnitController {

    @GetMapping("/getall")
    @Secured(Role.Values.ROLE_USER)
    public CateringUnitsResponse getAll();

    @PostMapping("/create")
    @Secured(Role.Values.ROLE_OWNER)
    public ResponseEntity create(@RequestBody CateringUnitRequest cateringUnitRequest);

    @PutMapping("/update/{id}")
    @Secured(Role.Values.ROLE_OWNER)
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody CateringUnitRequest cateringUnitRequest);

    @DeleteMapping("/delete/{id}")
    @Secured(Role.Values.ROLE_OWNER)
    public ResponseEntity delete(@PathVariable("id") Long id);
}
