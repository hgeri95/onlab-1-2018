package bme.cateringunitmonitor.api.remoting.service;

import bme.cateringunitmonitor.api.dto.CateringUnitRequest;
import bme.cateringunitmonitor.api.dao.CateringUnitDAO;
import bme.cateringunitmonitor.api.exception.CateringUnitServiceException;

import java.util.List;

public interface ICateringUnitService {

    String REMOTE_ENDPOINT = "/remoteCateringUnitService";

    public List<CateringUnitDAO> getAll();

    public CateringUnitDAO create(CateringUnitRequest cateringUnitRequest) throws CateringUnitServiceException;

    public CateringUnitDAO update(Long id, CateringUnitRequest cateringUnitRequest) throws CateringUnitServiceException;

    public void delete(Long id) throws CateringUnitServiceException;
}
