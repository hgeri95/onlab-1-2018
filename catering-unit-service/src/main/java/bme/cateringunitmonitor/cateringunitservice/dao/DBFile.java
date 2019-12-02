package bme.cateringunitmonitor.cateringunitservice.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "files")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DBFile {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String cateringUnit;

    private String fileName;

    private String fileType;

    @Lob
    private byte[] data;

    public DBFile(String cateringUnit, String fileName, String fileType, byte[] data) {
        this.cateringUnit = cateringUnit;
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }
}
