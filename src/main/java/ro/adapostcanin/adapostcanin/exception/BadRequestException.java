package ro.adapostcanin.adapostcanin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    public static Supplier<BadRequestException> supply(String message) {
        return () -> new BadRequestException(message);
    }

    public BadRequestException(String message) {
        super(message);
    }

}
