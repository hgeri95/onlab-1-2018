package bme.cateringunitmonitor.cateringunitservice.service;

import bme.cateringunitmonitor.api.exception.FileServiceException;
import bme.cateringunitmonitor.cateringunitservice.dao.DBFile;
import bme.cateringunitmonitor.cateringunitservice.repository.DBFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

@Service
@Slf4j
public class FileStorageService {

    @Autowired
    private DBFileRepository fileRepository;

    private List<String> allowedTypes = asList("image/jpeg", "image/jpg", "image/png");

    public DBFile store(MultipartFile file, String cateringUnitName) throws FileServiceException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        log.info("Upload file: {} for: {}", fileName, cateringUnitName);

        if (!allowedTypes.contains(file.getContentType())) {//TODO check
            throw new FileServiceException("File formaet not supported! Use one of these: " + allowedTypes.toString());
        }

        try {
            if (fileName.contains("..")) {
                throw new FileServiceException("Invalid filename!");
            }

            DBFile dbFile = new DBFile(cateringUnitName, fileName, file.getContentType(), file.getBytes());
            return fileRepository.save(dbFile);
        } catch (IOException ex) {
            log.error("Failed to save file: {}", ex.toString());
            throw new FileServiceException("Failed to upload file: " + ex.getMessage());
        }
    }

    public DBFile getFile(String fileId) throws FileServiceException {
        log.debug("Get file: {}", fileId);
        return fileRepository.findById(fileId).
                orElseThrow(() -> new FileServiceException("File not found with id: " + fileId));
    }

    public List<String> getFileIdsForCatering(String cateringName) {
        log.debug("Get file ids for catering: {}", cateringName);
        return fileRepository.findAllByCateringUnit(cateringName)
                .stream().map(DBFile::getId).collect(Collectors.toList());
    }

    public void deleteFileById(String fileId) {
        log.debug("Delete file: {}", fileId);
        fileRepository.deleteById(fileId);
    }
}
