package bme.cateringunitmonitor.remoting.controller;

import bme.cateringunitmonitor.entities.cateringunit.api.CateringUnitRequest;
import bme.cateringunitmonitor.entities.cateringunit.api.CateringUnitsResponse;
import bme.cateringunitmonitor.entities.user.entity.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ICateringUnitController {

    @GetMapping("/getall")
    @Secured(Role.Values.ROLE_USER)
    public CateringUnitsResponse getAll();

    @PostMapping("/create")
    @Secured(Role.Values.ROLE_OWNER)
    public ResponseEntity create(@RequestBody CateringUnitRequest cateringUnitRequest);

    @PutMapping("/update")
    @Secured(Role.Values.ROLE_OWNER)
    public ResponseEntity update(@RequestBody CateringUnitRequest cateringUnitRequest);
}
