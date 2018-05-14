package bme.cateringunitmonitor.remoting.service;

import bme.cateringunitmonitor.entities.cateringunit.api.CateringUnitRequest;
import bme.cateringunitmonitor.entities.cateringunit.entity.CateringUnit;
import bme.cateringunitmonitor.entities.exception.CateringUnitServiceException;

import java.util.List;

public interface ICateringUnitService {

    String REMOTE_ENDPOINT = "/remoteCateringUnitService";

    public List<CateringUnit> getAll();

    public CateringUnit create(CateringUnitRequest cateringUnitRequest) throws CateringUnitServiceException;

    public CateringUnit update(Long id, CateringUnitRequest cateringUnitRequest) throws CateringUnitServiceException;

    public void delete(Long id) throws CateringUnitServiceException;
}
