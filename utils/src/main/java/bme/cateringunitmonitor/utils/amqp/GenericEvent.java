package bme.cateringunitmonitor.utils.amqp;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class GenericEvent {

    private String eventName;
    private String eventParameterName;
    private String eventParameter;

    public GenericEvent(String eventName, String eventParameterName, String eventParameter) {
        this.eventName = eventName;
        this.eventParameterName = eventParameterName;
        this.eventParameter = eventParameter;
    }

    public GenericEvent() {}

    public String getMessage() {
        return eventName + EventTypes.SEPARATOR + eventParameterName + EventTypes.SEPARATOR + eventParameter;
    }

    public void deserializeMessage(String message) {
        String[] messageParts = message.split(EventTypes.SEPARATOR);
        eventName = messageParts[0];
        eventParameterName = messageParts[1];
        eventParameter = messageParts[2];
    }
}
