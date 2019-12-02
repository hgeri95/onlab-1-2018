package bme.cateringunitmonitor.cateringunitservice.controller;

import bme.cateringunitmonitor.api.dto.UploadFileResponse;
import bme.cateringunitmonitor.api.exception.FileServiceException;
import bme.cateringunitmonitor.api.remoting.controller.IFileController;
import bme.cateringunitmonitor.cateringunitservice.dao.DBFile;
import bme.cateringunitmonitor.cateringunitservice.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@Slf4j
public class FileController implements IFileController {

    @Autowired
    private FileStorageService fileService;

    @Override
    public UploadFileResponse uploadFile(String cateringUnitName, MultipartFile file) {
        try {
            DBFile dbFile = fileService.store(file, cateringUnitName);
            return new UploadFileResponse(dbFile.getFileName(), dbFile.getId(),
                    dbFile.getFileType(), file.getSize(), dbFile.getCateringUnit());
        } catch (FileServiceException ex) {
            log.warn("Failed to upload file: {}", ex.toString());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @Override
    public ResponseEntity<Resource> downloadFile(String fileId) {
        try {
            DBFile dbFile = fileService.getFile(fileId);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                    .body(new ByteArrayResource(dbFile.getData()));

        } catch (FileServiceException ex) {
            log.warn("Failed to download file: {}", ex.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @Override
    public List<String> getImageIdsForCatering(String cateringUnitName) {
        return fileService.getFileIdsForCatering(cateringUnitName);
    }

    @Override
    public void deleteImageById(String fileId) {
        fileService.deleteFileById(fileId);
    }
}
