package bme.cateringunitmonitor.api.remoting.controller;

import bme.cateringunitmonitor.api.Role;
import bme.cateringunitmonitor.api.dto.CateringUnitDTO;
import bme.cateringunitmonitor.api.dto.CateringUnitRequest;
import bme.cateringunitmonitor.api.dto.CateringUnitsResponse;
import bme.cateringunitmonitor.feign.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@FeignClient(name = "CateringController", url = "${cateringServiceUrl}", configuration = FeignConfiguration.class)
public interface ICateringUnitController {

    String BASE_PATH = "api/v1/cateringunit";

    @GetMapping(BASE_PATH + "/getall")
    @Secured(Role.Values.ROLE_USER)
    public CateringUnitsResponse getAll();

    //TODO Search nice to have

    @DeleteMapping(BASE_PATH + "/delete/{id}")
    @Secured(Role.Values.ROLE_OWNER)
    public ResponseEntity delete(@PathVariable("id") Long id);

    @PostMapping(BASE_PATH + "/create")
    @Secured(Role.Values.ROLE_OWNER)
    public ResponseEntity<CateringUnitDTO> create(@RequestBody @Valid CateringUnitRequest cateringUnitRequest);

    @PutMapping(BASE_PATH + "/update/{id}")
    @Secured(Role.Values.ROLE_OWNER)
    public ResponseEntity<CateringUnitDTO > update(@PathVariable("id") Long id,
                                                   @RequestBody @Valid CateringUnitRequest cateringUnitRequest);

    @GetMapping(BASE_PATH + "/get/{id}")
    @Secured({Role.Values.ROLE_OWNER, Role.Values.ROLE_USER})
    public ResponseEntity<CateringUnitDTO> get(@PathVariable("id") Long id);

    @GetMapping(BASE_PATH + "/exists/{cateringUnitName}")
    public Boolean checkCateringUnitExists(@PathVariable("cateringUnitName") String cateringUnitName);
}
