package bme.cateringunitmonitor.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class CateringUnitHttpException extends HttpStatusCodeException {
    public CateringUnitHttpException(HttpStatus statusCode) {
        super(statusCode);
    }

    public CateringUnitHttpException(HttpStatus statusCode, String statusText) {
        super(statusCode, statusText);
    }
}
