package bme.cateringunitmonitor.apigateway.controller;

import bme.cateringunitmonitor.security.SecurityConstants;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class DummyController implements IDummyController {

    @Override
    public List<String> getDummys(@RequestHeader(SecurityConstants.HEADER_STRING) String token) {
        return Arrays.asList("A", "B");
    }
}
