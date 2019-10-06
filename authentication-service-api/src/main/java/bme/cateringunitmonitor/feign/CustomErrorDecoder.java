package bme.cateringunitmonitor.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {
    private static final String MESSAGE_KEY = "message";
    private final ErrorDecoder defaultDecoder = new Default();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String s, Response response) {
        try {
            log.debug("Feign decode error: {}", s);
            String responseBody = Util.toString(response.body().asReader());
            String message = objectMapper.readTree(responseBody).get(MESSAGE_KEY).asText();
            return new ResponseStatusException(HttpStatus.valueOf(response.status()), message);
        } catch (IOException ex) {
            log.warn("Failed to parse exception: {}", ex);
        }
        return defaultDecoder.decode(s, response);
    }
}
