package bme.cateringunitmonitor.remoting.service;

import bme.cateringunitmonitor.entities.cateringunit.api.CateringUnitRequest;
import bme.cateringunitmonitor.entities.cateringunit.entity.CateringUnit;

import java.util.List;

public interface ICateringUnitService {

    String REMOTE_ENDPOINT = "/remoteCateringUnitService";

    public List<CateringUnit> getAll();

    public boolean create(CateringUnitRequest cateringUnitRequest);

    public boolean update(CateringUnitRequest cateringUnitRequest);
}
