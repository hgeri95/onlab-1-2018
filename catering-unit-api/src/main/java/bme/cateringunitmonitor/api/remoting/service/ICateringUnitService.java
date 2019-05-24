package bme.cateringunitmonitor.api.remoting.service;

import bme.cateringunitmonitor.api.dto.CateringUnitDTO;
import bme.cateringunitmonitor.api.exception.CateringUnitServiceException;

import java.util.List;

public interface ICateringUnitService {

    String REMOTE_ENDPOINT = "/remoteCateringUnitService";

    public List<CateringUnitDTO> getAll();

    public CateringUnitDTO create(CateringUnitDTO cateringUnitRequest) throws CateringUnitServiceException;

    public CateringUnitDTO update(Long id, CateringUnitDTO cateringUnitRequest) throws CateringUnitServiceException;

    public void delete(Long id) throws CateringUnitServiceException;

    public CateringUnitDTO get(Long id);
}
