package bme.cateringunitmonitor.rating;

import bme.cateringunitmonitor.api.Role;
import bme.cateringunitmonitor.api.dto.CateringUnitsResponse;
import bme.cateringunitmonitor.feign.FeignConfiguration;
import bme.cateringunitmonitor.rating.dto.RatingRequest;
import bme.cateringunitmonitor.rating.dto.RatingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@FeignClient(name = "RatingController", url = "${ratingServiceUrl}", configuration = FeignConfiguration.class)
public interface IRatingController {

    String BASE_PATH = "api/v1/rating";

    @PutMapping(BASE_PATH + "/rate")
    @Secured(Role.Values.ROLE_USER)
    public RatingResponse changeRate(@RequestBody @Valid RatingRequest ratingRequest);

    @GetMapping(BASE_PATH + "/{cateringUnitName}")
    @Secured({Role.Values.ROLE_USER, Role.Values.ROLE_OWNER, Role.Values.ROLE_TECHNICAL})
    public List<RatingResponse> getAllRatingForCateringUnit(@PathVariable @NotBlank String cateringUnitName);

    /*@GetMapping(BASE_PATH + "/user/{userName}")
    @Secured(Role.Values.ROLE_USER)
    public List<RatingResponse> getAllRatingForUser(@PathVariable("userName") @NotBlank String username);*/

    @GetMapping(BASE_PATH + "/specific")
    @Secured(Role.Values.ROLE_USER)
    public RatingResponse getRatingForUserAndCateringUnit(@RequestParam("cateringUnitName") @NotBlank String cateringUnitName);

    @GetMapping(BASE_PATH + "/recommended")
    @Secured({Role.Values.ROLE_USER, Role.Values.ROLE_ADMIN})
    public CateringUnitsResponse getRecommendedCaterings();
}
