package bme.cateringunitmonitor.utils.date;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZoneOffset;

@Slf4j
@Service
public class DateUtil {

    public static String TIME_ZONE;

    public static ZoneId getZoneId() {
        return ZoneId.of(TIME_ZONE);
    }

    public static ZoneOffset getZoneOffset() {
        return ZoneOffset.of(getZoneId().getId());
    }

    @Value("${custom.time.zone:Europe/Paris}")
    public void setTimeZone(String timeZone) {
        log.info("Time zone set to: {}", timeZone);
        TIME_ZONE = timeZone;
    }
}
