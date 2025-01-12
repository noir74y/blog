package ru.noir74.blog.util.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.noir74.blog.util.exception.*;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> exceptionController(Exception exception) {
        AppException appException;

        if (exception instanceof NotFoundException)
            appException = (NotFoundException) exception;
        else if (exception instanceof ForbiddenException)
            appException = (ForbiddenException) exception;
        else if (exception instanceof CustomValidationException)
            appException = (CustomValidationException) exception;
        else
            appException = new OtherException(exception);

        return ResponseEntity.status(appException.getHttpErrorStatus()).body(appException.prepareErrorMessage());
    }
}
