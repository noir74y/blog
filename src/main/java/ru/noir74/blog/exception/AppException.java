package ru.noir74.blog.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import ru.noir74.blog.exception.handler.ErrorMessage;


public class AppException extends RuntimeException {
    @Getter
    protected HttpStatus httpErrorStatus;
    protected String cause;
    protected String message;

    public ErrorMessage prepareErrorMessage() {
        return new ErrorMessage(cause, message);
    }

}
