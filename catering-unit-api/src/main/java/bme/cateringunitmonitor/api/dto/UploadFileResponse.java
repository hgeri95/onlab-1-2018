package bme.cateringunitmonitor.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UploadFileResponse {
    private String fileName;
    private String fileId;
    private String fileType;
    private long size;
    private String cateringUnitName;


}
