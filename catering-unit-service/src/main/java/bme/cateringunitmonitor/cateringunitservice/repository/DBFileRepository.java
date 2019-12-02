package bme.cateringunitmonitor.cateringunitservice.repository;

import bme.cateringunitmonitor.cateringunitservice.dao.DBFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DBFileRepository extends JpaRepository<DBFile, String> {

    public List<DBFile> findAllByCateringUnit(String cateringUnit);
}
