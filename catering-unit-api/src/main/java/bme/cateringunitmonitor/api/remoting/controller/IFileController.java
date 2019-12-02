package bme.cateringunitmonitor.api.remoting.controller;

import bme.cateringunitmonitor.api.Role;
import bme.cateringunitmonitor.api.dto.UploadFileResponse;
import bme.cateringunitmonitor.feign.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "FileController", url = "${cateringServiceUrl}", configuration = FeignConfiguration.class)
public interface IFileController {
    String BASE_PATH = "/api/v1/cateringunit/image";

    @PostMapping(BASE_PATH + "/upload/{cateringUnitName}")
    @Secured({Role.Values.ROLE_OWNER, Role.Values.ROLE_ADMIN})
    public UploadFileResponse uploadFile(@PathVariable("cateringUnitName") String cateringUnitName, @RequestParam("file") MultipartFile file);

    @GetMapping(BASE_PATH + "/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileId") String fileId);

    @GetMapping(BASE_PATH + "/getIds/{cateringUnitName}")
    @Secured({Role.Values.ROLE_USER, Role.Values.ROLE_OWNER, Role.Values.ROLE_ADMIN})
    public List<String> getImageIdsForCatering(@PathVariable("cateringUnitName") String cateringUnitName);

    @DeleteMapping(BASE_PATH + "/{fileId}")
    @Secured({Role.Values.ROLE_ADMIN, Role.Values.ROLE_OWNER})
    public void deleteImageById(@PathVariable("fileId") String fileId);

}
