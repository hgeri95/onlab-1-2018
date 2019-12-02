package bme.cateringunitmonitor.rating.util;

import bme.cateringunitmonitor.rating.dao.RatingDAO;
import bme.cateringunitmonitor.rating.dto.RatingRequest;
import bme.cateringunitmonitor.rating.dto.RatingResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RatingConverter {

    @Autowired
    private ModelMapper modelMapper;

    public RatingResponse convertToDTO(RatingDAO ratingDAO) {
        return modelMapper.map(ratingDAO, RatingResponse.class);
    }
}
