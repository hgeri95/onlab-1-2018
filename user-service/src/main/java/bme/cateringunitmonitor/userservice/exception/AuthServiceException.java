package bme.cateringunitmonitor.userservice.exception;

import javax.naming.AuthenticationException;

public class AuthServiceException extends AuthenticationException {

    public AuthServiceException(String explanation) {
        super(explanation);
    }

    @Override
    public String getExplanation() {
        return super.getExplanation();
    }
}
