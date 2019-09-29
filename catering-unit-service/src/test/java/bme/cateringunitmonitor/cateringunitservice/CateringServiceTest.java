package bme.cateringunitmonitor.cateringunitservice;

import bme.cateringunitmonitor.api.*;
import bme.cateringunitmonitor.api.dto.CateringUnitDTO;
import bme.cateringunitmonitor.api.dto.CateringUnitRequest;
import bme.cateringunitmonitor.api.exception.CateringUnitServiceException;
import bme.cateringunitmonitor.cateringunitservice.service.CateringUnitService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(TestConfig.class)
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
        CateringUnitDTO result = cateringUnitService.create(cateringUnit);
        assertNotNull(result);
        assertEquals(1, cateringUnitService.getAll().size());

        String description = "updated dic";
        cateringUnit.setDescription(description);
        CateringUnitDTO updatedCateringUnit = cateringUnitService.update(result.getId(), cateringUnit);
        assertEquals(description, updatedCateringUnit.getDescription());

        cateringUnitService.delete(updatedCateringUnit.getId());
        assertEquals(0, cateringUnitService.getAll().size());
    }
}
