package bme.cateringunitmonitor.apigateway.controller;

import bme.cateringunitmonitor.security.SecurityConstants;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "DummyClient", url = "http://localhost:8080")
public interface IDummyController {

    @RequestMapping(method = RequestMethod.GET, path = "/dummy")
    public List<String> getDummys(@RequestHeader(SecurityConstants.HEADER_STRING) String token);
}
