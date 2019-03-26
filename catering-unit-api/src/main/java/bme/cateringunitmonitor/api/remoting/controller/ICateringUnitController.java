package bme.cateringunitmonitor.api.remoting.controller;

import bme.cateringunitmonitor.api.Role;
import bme.cateringunitmonitor.api.dto.CateringUnitRequest;
import bme.cateringunitmonitor.api.dto.CateringUnitsResponse;
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
