package bme.cateringunitmonitor.rating.service;

import bme.cateringunitmonitor.api.remoting.controller.ICateringUnitController;
import bme.cateringunitmonitor.api.remoting.controller.IUserController;
import bme.cateringunitmonitor.rating.dao.RatingDAO;
import bme.cateringunitmonitor.rating.dto.RatingRequest;
import bme.cateringunitmonitor.rating.dto.RatingResponse;
import bme.cateringunitmonitor.rating.exception.RatingServiceException;
import bme.cateringunitmonitor.rating.repository.RatingRepository;
import bme.cateringunitmonitor.rating.util.RatingConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class RatingService {

    @Autowired
    private IUserController userController;

    @Autowired
    private ICateringUnitController cateringUnitController;

    @Autowired
    private RatingConverter ratingConverter;

    @Autowired
    private RatingRepository ratingRepository;

    public RatingResponse updateRate(RatingRequest ratingRequest) throws RatingServiceException {
        checkCateringUnitExists(ratingRequest.getCateringUnitName());
        checkUserExists(ratingRequest.getUsername());
        if (!isRatingValueValid(ratingRequest.getRate())) {
            throw new RatingServiceException("Rating value is not in 1 - 5 range!");
        }

        Optional<RatingDAO> existingRating = ratingRepository.findByUsernameAndCateringUnitName(
                ratingRequest.getUsername(), ratingRequest.getCateringUnitName());

        if (existingRating.isPresent()) {
            RatingDAO updatedRating = ratingConverter.convertToEntity(ratingRequest);
            updatedRating.setId(existingRating.get().getId());
            log.info("Rating updated to: {}", updatedRating);
            return ratingConverter.convertToDTO(ratingRepository.save(updatedRating));
        } else {
            RatingDAO ratingToSave = ratingConverter.convertToEntity(ratingRequest);
            log.info("New rating to save: {}", ratingToSave);
            return ratingConverter.convertToDTO(ratingRepository.save(ratingToSave));
        }
    }

    public List<RatingResponse> getAllRatingForCateringUnit(String cateringUnitName) throws RatingServiceException {
        log.debug("Get all rating for catering unit: {}", cateringUnitName);
        checkCateringUnitExists(cateringUnitName);

        List<RatingDAO> allRatingsForCateringUnit = ratingRepository.findAllByCateringUnitName(cateringUnitName);
        log.debug("Find {} ratings for catering unit: {}", allRatingsForCateringUnit.size(), cateringUnitName);

        return allRatingsForCateringUnit.stream().map(r -> ratingConverter.convertToDTO(r))
                .collect(Collectors.toList());

    }

    public List<RatingResponse> getAllRatingForUser(String username) throws RatingServiceException {
        log.debug("Get all ratings for user: {}", username);
        checkUserExists(username);

        List<RatingDAO> allRatingsForUser = ratingRepository.findAllByUsername(username);
        log.debug("Find {} ratings for user: {}", allRatingsForUser.size(), username);
        return allRatingsForUser.stream().map(r -> ratingConverter.convertToDTO(r)).collect(Collectors.toList());
    }

    public Optional<RatingResponse> getRatingForUserAndCateringUnit(String username, String cateringUnitName) throws RatingServiceException {
        log.debug("Get rating for user: {} and catering unit: {}", username, cateringUnitName);
        checkUserExists(username);
        checkCateringUnitExists(cateringUnitName);

        Optional<RatingDAO> rating = ratingRepository.findByUsernameAndCateringUnitName(username, cateringUnitName);
        if (rating.isPresent()) {
            log.debug("Rating found: {}", rating);
            return Optional.of(ratingConverter.convertToDTO(rating.get()));
        } else {
            log.debug("Rating not found.");
            return Optional.empty();
        }
    }

    public void deleteRatingsByCateringUnitName(String cateringUnitName) {
        log.debug("Delete ratings by catering unit name: {}", cateringUnitName);
        int deletedRatings = ratingRepository.deleteAllByCateringUnitName(cateringUnitName);
        log.debug("{} ratings deleted", deletedRatings);
    }

    public void deleteRatingsByUsername(String username) {
        log.debug("Delete ratings by username: {}", username);
        int deletedRatings = ratingRepository.deleteAllByUsername(username);
        log.debug("{} ratings deleted", deletedRatings);
    }

    private boolean checkCateringUnitExists(String cateringUnitName) throws RatingServiceException {
        boolean cateringUnitExists = cateringUnitController.checkCateringUnitExists(cateringUnitName);
        if (!cateringUnitExists) {
            log.warn("Catering Unit does not exists: {}", cateringUnitName);
            throw new RatingServiceException("Catering Unit does not exists!");
        }
        return cateringUnitExists;
    }

    private boolean checkUserExists(String username) throws RatingServiceException {
        boolean userExists = userController.checkUserExists(username);
        if (!userExists) {
            log.warn("User does not exists: {}", username);
            throw new RatingServiceException("User does not exists!");
        }
        return userExists;
    }

    private boolean isRatingValueValid(int rating) {
        return 1 <= rating && rating <= 5;
    }
}
