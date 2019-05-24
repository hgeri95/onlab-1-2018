package bme.cateringunitmonitor.api.remoting.controller;

import bme.cateringunitmonitor.api.Role;
import bme.cateringunitmonitor.api.dto.CateringUnitDTO;
import bme.cateringunitmonitor.api.dto.CateringUnitsResponse;
import bme.cateringunitmonitor.utils.feign.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "CateringController", url = "${cateringServiceUrl}", configuration = FeignConfiguration.class)
public interface ICateringUnitController {

    @GetMapping("/cateringunit/getall")
    @Secured(Role.Values.ROLE_USER)
    public CateringUnitsResponse getAll();

    //TODO Search nice to have

    @DeleteMapping("/cateringunit/delete/{id}")
    @Secured(Role.Values.ROLE_OWNER)
    public ResponseEntity delete(@PathVariable("id") Long id);

    @PostMapping("/cateringunit/create")
    @Secured(Role.Values.ROLE_OWNER)
    public ResponseEntity<CateringUnitDTO> create(@RequestBody CateringUnitDTO cateringUnitRequest);

    @PutMapping("/cateringunit/update/{id}")
    @Secured(Role.Values.ROLE_OWNER)
    public ResponseEntity<CateringUnitDTO > update(@PathVariable("id") Long id, @RequestBody CateringUnitDTO cateringUnitRequest);

    @GetMapping("/cateringunit/get/{id}")
    @Secured({Role.Values.ROLE_OWNER, Role.Values.ROLE_USER})
    public ResponseEntity<CateringUnitDTO> get(@PathVariable("id") Long id);
}
