package bme.cateringunitmonitor.apigateway.controller;

import bme.cateringunitmonitor.security.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/feign")
public class FeignController {

    @Autowired
    IDummyController dummyController;

    @RequestMapping(method = RequestMethod.GET, path = "/dummy")
    public List<String> getDummys(@RequestHeader(SecurityConstants.HEADER_STRING) String token) {
        return dummyController.getDummys(token);
    }
}
