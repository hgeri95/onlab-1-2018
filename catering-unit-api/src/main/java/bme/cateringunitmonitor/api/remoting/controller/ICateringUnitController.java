package bme.cateringunitmonitor.api.remoting.controller;

import bme.cateringunitmonitor.api.Role;
import bme.cateringunitmonitor.api.dto.CateringUnitDTO;
import bme.cateringunitmonitor.api.dto.CateringUnitRequest;
import bme.cateringunitmonitor.api.dto.CateringUnitWithDistanceDTO;
import bme.cateringunitmonitor.api.dto.CateringUnitsResponse;
import bme.cateringunitmonitor.feign.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "CateringController", url = "${cateringServiceUrl}", configuration = FeignConfiguration.class)
public interface ICateringUnitController {

    String BASE_PATH = "api/v1/cateringunit";

    @GetMapping(BASE_PATH + "/all")
    @Secured({Role.Values.ROLE_USER, Role.Values.ROLE_ADMIN})
    public CateringUnitsResponse getAll();

    @GetMapping(BASE_PATH + "/owned")
    @Secured({Role.Values.ROLE_OWNER, Role.Values.ROLE_ADMIN})
    public CateringUnitsResponse getOwned();

    @DeleteMapping(BASE_PATH + "/{id}")
    @Secured({Role.Values.ROLE_OWNER, Role.Values.ROLE_ADMIN})
    public ResponseEntity delete(@PathVariable("id") Long id);

    @PostMapping(BASE_PATH)
    @Secured(Role.Values.ROLE_OWNER)
    public ResponseEntity<CateringUnitDTO> create(@RequestBody @Valid CateringUnitRequest cateringUnitRequest);

    @PutMapping(BASE_PATH + "/{id}")
    @Secured(Role.Values.ROLE_OWNER)
    public ResponseEntity<CateringUnitDTO > update(@PathVariable("id") Long id,
                                                   @RequestBody @Valid CateringUnitRequest cateringUnitRequest);

    @GetMapping(BASE_PATH + "/{id}")
    @Secured({Role.Values.ROLE_OWNER, Role.Values.ROLE_USER})
    public ResponseEntity<CateringUnitDTO> get(@PathVariable("id") Long id);

    @GetMapping(BASE_PATH + "/exists/{cateringUnitName}")
    public Boolean checkCateringUnitExists(@PathVariable("cateringUnitName") String cateringUnitName);

    @GetMapping(BASE_PATH + "/search")
    public List<CateringUnitDTO> search(@RequestParam(name = "term", required=true) String searchTerm);

    @GetMapping(BASE_PATH + "/nearest")
    @Secured({Role.Values.ROLE_ADMIN, Role.Values.ROLE_USER})
    public List<CateringUnitWithDistanceDTO> getNearestCaterings(
            @RequestParam(name="distance", required = true) double distance,
            @RequestParam(name="latitude", required = true) double latitude,
            @RequestParam(name="longitude", required = true) double longitude
    );
}
