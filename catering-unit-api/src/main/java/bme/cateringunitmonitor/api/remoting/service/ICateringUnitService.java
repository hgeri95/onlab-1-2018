package bme.cateringunitmonitor.api.remoting.service;

import bme.cateringunitmonitor.api.dto.CateringUnitRequest;
import bme.cateringunitmonitor.api.dao.CateringUnit;
import bme.cateringunitmonitor.api.exception.CateringUnitServiceException;

import java.util.List;

public interface ICateringUnitService {

    String REMOTE_ENDPOINT = "/remoteCateringUnitService";

    public List<CateringUnit> getAll();

    public CateringUnit create(CateringUnitRequest cateringUnitRequest) throws CateringUnitServiceException;

    public CateringUnit update(Long id, CateringUnitRequest cateringUnitRequest) throws CateringUnitServiceException;

    public void delete(Long id) throws CateringUnitServiceException;
}
