package bme.cateringunitmonitor.rating;

import bme.cateringunitmonitor.api.remoting.controller.ICateringUnitController;
import bme.cateringunitmonitor.api.remoting.controller.IUserController;
import bme.cateringunitmonitor.rating.dto.RatingRequest;
import bme.cateringunitmonitor.rating.dto.RatingResponse;
import bme.cateringunitmonitor.rating.exception.RatingServiceException;
import bme.cateringunitmonitor.rating.service.RatingService;
import bme.cateringunitmonitor.rating.util.RatingConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigurationPackage
public class RatingServiceTest {

    @Configuration
    static class ContextConfiguration {
        @Bean
        public RatingConverter ratingConverter() {
            return new RatingConverter();
        }

        @Bean
        public ModelMapper modelMapper() {
            return new ModelMapper();
        }

        @Bean
        public IUserController userController() {
            IUserController userController = mock(IUserController.class);
            when(userController.checkUserExists(any())).thenReturn(true);
            return userController;
        }

        @Bean
        public ICateringUnitController cateringUnitController() {
            ICateringUnitController cateringUnitController = mock(ICateringUnitController.class);
            when(cateringUnitController.checkCateringUnitExists(any())).thenReturn(true);
            return cateringUnitController;
        }

        @Bean
        public RatingService ratingService() {
            return new RatingService();
        }
    }

    @Autowired
    private RatingService ratingService;

    @Test
    public void testRatingCreationAndUpdate() throws RatingServiceException {
        String username = "testuser1";
        String cateringUnitName = "catering1";

        RatingRequest ratingRequest = new RatingRequest(cateringUnitName, 1, "comment");
        ratingService.updateRate(username, ratingRequest);

        Optional<RatingResponse> ratingResponse = ratingService
                .getRatingForUserAndCateringUnit(username, cateringUnitName);

        assertEquals(username, ratingResponse.get().getUsername());
        assertEquals(ratingRequest.getCateringUnitName(), ratingResponse.get().getCateringUnitName());
        assertEquals(ratingRequest.getRate(), ratingResponse.get().getRate());

        RatingRequest updateRequest = new RatingRequest(cateringUnitName, 5, "comment");
        ratingService.updateRate(username, updateRequest);

        ratingResponse = ratingService.getRatingForUserAndCateringUnit(username, cateringUnitName);
        assertEquals(updateRequest.getRate(), ratingResponse.get().getRate());

        assertEquals(1, ratingService.getAllRatingForUser(username).size());
        assertEquals(1, ratingService.getAllRatingForCateringUnit(cateringUnitName).size());
    }
}
