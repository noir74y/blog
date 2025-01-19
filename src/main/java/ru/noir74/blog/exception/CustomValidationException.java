package ru.noir74.blog.exception;

import org.springframework.http.HttpStatus;

public class CustomValidationException extends AppException {
    public CustomValidationException(String message, String details) {
        httpErrorStatus = HttpStatus.BAD_REQUEST;
        this.cause = message;
        this.message = details;
    }
}

