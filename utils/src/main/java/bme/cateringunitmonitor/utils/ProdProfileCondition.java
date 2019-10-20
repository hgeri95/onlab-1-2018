package bme.cateringunitmonitor.utils;

import org.springframework.core.env.Environment;

import java.util.Arrays;

import static bme.cateringunitmonitor.utils.Profiles.TEST_PROFILE;

public class ProdProfileCondition extends ProfileCondition {
    public boolean matchProfiles(final Environment environment) {
        return Arrays.stream(environment.getActiveProfiles()).noneMatch(prof -> prof.equals(TEST_PROFILE));
    }
}
