package bme.cateringunitmonitor.rating.controller;

import bme.cateringunitmonitor.api.dto.CateringUnitsResponse;
import bme.cateringunitmonitor.rating.IRatingController;
import bme.cateringunitmonitor.rating.dto.RatingRequest;
import bme.cateringunitmonitor.rating.dto.RatingResponse;
import bme.cateringunitmonitor.rating.exception.RatingServiceException;
import bme.cateringunitmonitor.rating.service.RatingService;
import bme.cateringunitmonitor.security.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class RatingController implements IRatingController {

    @Autowired
    private RatingService ratingService;

    @Override
    public RatingResponse changeRate(@Valid RatingRequest ratingRequest) {
        try {
            String username = SecurityUtil.getActiveUser();
            return ratingService.updateRate(username, ratingRequest);
        } catch (RatingServiceException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @Override
    public List<RatingResponse> getAllRatingForCateringUnit(String cateringUnitName) {
        try {
            return ratingService.getAllRatingForCateringUnit(cateringUnitName);
        } catch (RatingServiceException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

   // @Override
    public List<RatingResponse> getAllRatingForUser(String username) {
        try {
            return ratingService.getAllRatingForUser(username);
        } catch (RatingServiceException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @Override
    public RatingResponse getRatingForUserAndCateringUnit(@NotBlank String cateringUnitName) {
        String username = SecurityUtil.getActiveUser();
        try {
            Optional<RatingResponse> ratingResponse =
                    ratingService.getRatingForUserAndCateringUnit(username, cateringUnitName);
            if (ratingResponse.isPresent()) {
                return ratingResponse.get();
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        } catch (RatingServiceException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @Override
    public CateringUnitsResponse getRecommendedCaterings() {
        String username = SecurityUtil.getActiveUser();
        return ratingService.getRecommended(username);
    }
}
