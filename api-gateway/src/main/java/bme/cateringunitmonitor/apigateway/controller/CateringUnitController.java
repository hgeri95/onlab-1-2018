package bme.cateringunitmonitor.apigateway.controller;

import bme.cateringunitmonitor.api.Role;
import bme.cateringunitmonitor.api.dto.CateringUnitDTO;
import bme.cateringunitmonitor.api.dto.CateringUnitsResponse;
import bme.cateringunitmonitor.api.remoting.controller.ICateringUnitController;
import bme.cateringunitmonitor.utils.feign.CustomErrorDecoder;
import bme.cateringunitmonitor.utils.feign.FeignSecurityInterceptor;
import feign.Contract;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/cateringunit")
public class CateringUnitController {

    @Autowired
    private ICateringUnitController cateringUnitController;

    @GetMapping("/getall")
    @Secured(Role.Values.ROLE_USER)
    public CateringUnitsResponse getAll() {
        return cateringUnitController.getAll();
    }

    @DeleteMapping("/delete/{id}")
    @Secured(Role.Values.ROLE_OWNER)
    public ResponseEntity delete(@PathVariable("id") Long id) {
        return cateringUnitController.delete(id);
    }

    @PostMapping("/create")
    @Secured(Role.Values.ROLE_OWNER)
    public ResponseEntity create(@Valid @RequestBody CateringUnitDTO cateringUnitRequest) {
        return cateringUnitController.create(cateringUnitRequest);
    }

    @PutMapping("/update/{id}")
    @Secured(Role.Values.ROLE_OWNER)
    public ResponseEntity update(@Valid @PathVariable("id") Long id, @RequestBody CateringUnitDTO cateringUnitRequest) {
        return cateringUnitController.update(id, cateringUnitRequest);
    }

    @GetMapping("/get/{id}")
    @Secured({Role.Values.ROLE_OWNER, Role.Values.ROLE_USER})
    public ResponseEntity get(@PathVariable("id") Long id) {
        return cateringUnitController.get(id);
    }
}
