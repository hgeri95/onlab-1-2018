package bme.cateringunitmonitor.rating.service;

import bme.cateringunitmonitor.api.dto.CateringUnitDTO;
import bme.cateringunitmonitor.api.dto.CateringUnitRequest;
import bme.cateringunitmonitor.api.dto.CateringUnitsResponse;
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

import java.util.*;
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

    public RatingResponse updateRate(String username, RatingRequest ratingRequest) throws RatingServiceException {
        checkCateringUnitExists(ratingRequest.getCateringUnitName());
        checkUserExists(username);
        if (!isRatingValueValid(ratingRequest.getRate())) {
            throw new RatingServiceException("Rating value is not in 1 - 5 range!");
        }

        Optional<RatingDAO> existingRating = ratingRepository.findByUsernameAndCateringUnitName(
                username, ratingRequest.getCateringUnitName());

        if (existingRating.isPresent()) {
            RatingDAO updatedRating = new RatingDAO(ratingRequest.getCateringUnitName(), username, ratingRequest.getRate(), ratingRequest.getComment());
            updatedRating.setId(existingRating.get().getId());
            log.info("Rating updated to: {}", updatedRating);
            return ratingConverter.convertToDTO(ratingRepository.save(updatedRating));
        } else {
            RatingDAO ratingToSave = new RatingDAO(ratingRequest.getCateringUnitName(), username, ratingRequest.getRate(), ratingRequest.getComment());
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

    public CateringUnitsResponse getRecommended(String username) {
        List<String> recommendedCateringNames = getRecommendedCateringsForUser(username);
        CateringUnitsResponse caterings = cateringUnitController.getAll();
        List<CateringUnitDTO> recommendedCaterings = caterings.getCateringUnits().stream()
                .filter(c -> recommendedCateringNames.contains(c.getName())).collect(Collectors.toList());
        return new CateringUnitsResponse(recommendedCaterings);
    }

    private List<String> getRecommendedCateringsForUser(String username) {
        log.info("Get recommended caterings for user: {}", username);
        List<RatingDAO> likedByUser = ratingRepository.findAllByUsername(username);
        List<String> userRatings = likedByUser.stream().map(RatingDAO::getCateringUnitName)
                .collect(Collectors.toList());
        List<String> likedCaterings = likedByUser.stream().filter(rating -> rating.getRate() > 2)
                .map(RatingDAO::getCateringUnitName).collect(Collectors.toList());

        List<RatingDAO> ratingsForSameCateringsByOthers = ratingRepository.findByCateringUnitNameIn(likedCaterings)
                .stream().filter(r -> !r.getUsername().equals(username))
                .sorted(Comparator.comparingInt(RatingDAO::getRate).reversed())
                .collect(Collectors.toList());
        log.debug("Ratings for same caterings: {}", ratingsForSameCateringsByOthers);

        Set<String> recommendedCaterings = new HashSet<>(); //TODO do no use db query in loop!!!
        for (RatingDAO rating : ratingsForSameCateringsByOthers) {
            List<String> likedCateringsFromSimilarUser = ratingRepository
                    .findAllByUsername(rating.getUsername()).stream().filter(r -> r.getRate() > 2)
                    .map(RatingDAO::getCateringUnitName).collect(Collectors.toList());
            likedCateringsFromSimilarUser.removeAll(userRatings);
            recommendedCaterings.addAll(likedCateringsFromSimilarUser);
            if (recommendedCaterings.size() > 10) {
                return new ArrayList<>(recommendedCaterings);
            }
        }
        return new ArrayList<>(recommendedCaterings);
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
