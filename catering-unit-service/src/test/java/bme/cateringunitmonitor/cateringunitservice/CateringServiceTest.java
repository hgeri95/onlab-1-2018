package bme.cateringunitmonitor.cateringunitservice;

import bme.cateringunitmonitor.api.*;
import bme.cateringunitmonitor.api.dto.CateringUnitDTO;
import bme.cateringunitmonitor.api.dto.CateringUnitRequest;
import bme.cateringunitmonitor.api.dto.CateringUnitWithDistanceDTO;
import bme.cateringunitmonitor.api.exception.CateringUnitServiceException;
import bme.cateringunitmonitor.cateringunitservice.service.CateringUnitService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static bme.cateringunitmonitor.utils.Profiles.TEST_PROFILE;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(TEST_PROFILE)
public class CateringServiceTest {

    @Autowired
    private CateringUnitService cateringUnitService;

    @Test
    public void testCateringUnitCrud() throws CateringUnitServiceException {
        CateringUnitRequest cateringUnit = new CateringUnitRequest(
                "McDonalds", "Best", asList(new OpeningPerDay(WeekDays.FRIDAY, "12:00", "13:00")),
                new Address("ABC Street 6.", new Coordinate(1, 1), "no"),
                asList(new CategoryParameter("type", "dfdg"))
        );
        CateringUnitDTO result = cateringUnitService.create(cateringUnit, "testUser");
        assertNotNull(result);
        assertEquals(1, cateringUnitService.getAll().size());

        String description = "updated dic";
        cateringUnit.setDescription(description);
        CateringUnitDTO updatedCateringUnit = cateringUnitService.update(result.getId(), cateringUnit, "testUser");
        assertEquals(description, updatedCateringUnit.getDescription());

        cateringUnitService.delete(updatedCateringUnit.getId());
        assertEquals(0, cateringUnitService.getAll().size());
    }

    @Test
    public void testNearestPlaces() throws CateringUnitServiceException {
        CateringUnitRequest cateringUnit = new CateringUnitRequest(
                "Burger king", "Best", asList(new OpeningPerDay(WeekDays.FRIDAY, "12:00", "13:00")),
                new Address("ABC Street 6.", new Coordinate( 19.032616, 47.463977), "no"),
                asList(new CategoryParameter("type", "dfdg"))
        );
        CateringUnitDTO result = cateringUnitService.create(cateringUnit, "testUser");
        assertNotNull(result);
        assertEquals(1, cateringUnitService.getAll().size());

        List<CateringUnitWithDistanceDTO> nearestCaterings = cateringUnitService
                .nearestCaterings(19.027778, 47.461743, 5.0);

        assertEquals(1, nearestCaterings.size());
    }
}
