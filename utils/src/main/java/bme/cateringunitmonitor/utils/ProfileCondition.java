package bme.cateringunitmonitor.utils;

import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public abstract class ProfileCondition extends SpringBootCondition {
    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        if (matchProfiles(conditionContext.getEnvironment())) {
            return ConditionOutcome.match("A local profile has been found.");
        }
        return ConditionOutcome.noMatch("No local profiles found.");
    }

    protected abstract boolean matchProfiles(final Environment environment);
}
