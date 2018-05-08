package bme.cateringunitmonitor.entities.exception;

public class CateringUnitServiceException extends RuntimeException {

    public CateringUnitServiceException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
