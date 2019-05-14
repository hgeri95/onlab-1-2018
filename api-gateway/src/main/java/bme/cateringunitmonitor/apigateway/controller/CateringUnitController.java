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

@RestController
public class CateringUnitController {

    private final ICateringUnitController cateringUnitController;

    @Autowired
    public CateringUnitController(@Value("${cateringServiceUrl}") String url, Decoder decoder, Encoder encoder, Contract contract,
                                  CustomErrorDecoder errorDecoder, FeignSecurityInterceptor securityInterceptor) {
        this.cateringUnitController = Feign.builder()
                .encoder(encoder)
                .decoder(decoder)
                .contract(contract)
                .errorDecoder(errorDecoder)
                .requestInterceptor(securityInterceptor)
                .target(ICateringUnitController.class, url);
    }

    @GetMapping("/cateringunit/getall")
    @Secured(Role.Values.ROLE_USER)
    public CateringUnitsResponse getAll() {
        return cateringUnitController.getAll();
    }

    @DeleteMapping("/cateringunit/delete/{id}")
    @Secured(Role.Values.ROLE_OWNER)
    public ResponseEntity delete(@PathVariable("id") Long id) {
        return cateringUnitController.delete(id);
    }

    @PostMapping("/cateringunit/create")
    @Secured(Role.Values.ROLE_OWNER)
    public ResponseEntity create(@RequestBody CateringUnitDTO cateringUnitRequest) {
        return cateringUnitController.create(cateringUnitRequest);
    }

    @PutMapping("/cateringunit/update/{id}")
    @Secured(Role.Values.ROLE_OWNER)
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody CateringUnitDTO cateringUnitRequest) {
        return cateringUnitController.update(id, cateringUnitRequest);
    }
}
