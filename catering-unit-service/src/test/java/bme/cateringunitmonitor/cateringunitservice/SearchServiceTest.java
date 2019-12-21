package bme.cateringunitmonitor.cateringunitservice;

import bme.cateringunitmonitor.api.*;
import bme.cateringunitmonitor.api.dto.CateringUnitDTO;
import bme.cateringunitmonitor.api.dto.CateringUnitRequest;
import bme.cateringunitmonitor.api.exception.CateringUnitServiceException;
import bme.cateringunitmonitor.cateringunitservice.service.CateringUnitService;
import bme.cateringunitmonitor.cateringunitservice.service.SearchService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static bme.cateringunitmonitor.utils.Profiles.TEST_PROFILE;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(TEST_PROFILE)
public class SearchServiceTest {

    @Autowired
    private SearchService searchService;

    @Autowired
    private CateringUnitService cateringUnitService;

    private static boolean isSetupDone = false;

    @Before
    public void init() throws CateringUnitServiceException {
        if (isSetupDone) {
            return;
        } else {
            uploadCateringUnits();
            isSetupDone = true;
        }
    }

    @Test
    public void testBasicSearch() {
        List<CateringUnitDTO> result = searchService.search("McDonalds");
        assertEquals(1, result.size());
    }

    @Test
    public void testSearchInCollections() {
        List<CateringUnitDTO> result = searchService.search("Bartok");
        assertEquals(1, result.size());

        result = searchService.search("zui");
        assertEquals(1, result.size());
    }

    @Test
    public void testSearchWithNotCompletedTerm() {
        List<CateringUnitDTO> result = searchService.search("burg*");
        assertEquals(2, result.size());
    }

    private void uploadCateringUnits() throws CateringUnitServiceException {
        CateringUnitRequest cateringUnit = new CateringUnitRequest(
                "McDonalds", "Best", asList(new OpeningPerDay(WeekDays.FRIDAY, "12:00", "13:00")),
                new Address("ABC Street 6.", new Coordinate(1, 1), "no"),
                asList(new CategoryParameter("type", "dfdg"))
        );
        CateringUnitRequest cateringUnit2 = new CateringUnitRequest(
                "Burger King", "Not bad", asList(new OpeningPerDay(WeekDays.FRIDAY, "12:00", "13:00")),
                new Address("DFG Street 6.", new Coordinate(1, 1), "no"),
                asList(new CategoryParameter("type", "asd"))
        );
        CateringUnitRequest cateringUnit3 = new CateringUnitRequest(
                "Trofea", "Very good", asList(new OpeningPerDay(WeekDays.FRIDAY, "12:00", "13:00")),
                new Address("Hadak Street 6.", new Coordinate(1, 1), "no"),
                asList(new CategoryParameter("type", "zui"))
        );
        CateringUnitRequest cateringUnit4 = new CateringUnitRequest(
                "Nagyon finom", "Come here", asList(new OpeningPerDay(WeekDays.FRIDAY, "12:00", "13:00")),
                new Address("Bartok Street 6.", new Coordinate(1, 1), "no"),
                asList(new CategoryParameter("type", "bnm"))
        );
        final String ownerName = "testUser";
        cateringUnitService.create(cateringUnit, ownerName);
        cateringUnitService.create(cateringUnit2, ownerName);
        cateringUnitService.create(cateringUnit3, ownerName);
        cateringUnitService.create(cateringUnit4, ownerName);
    }
}
