package br.com.timesync.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ApiErrorMessage> objectNotFound(ObjectNotFoundException e) {
        final HttpStatus status = HttpStatus.NOT_FOUND;
        final ApiErrorMessage apiErrorMessage = new ApiErrorMessage(e.getMessage());
        return ResponseEntity.status(status).body(apiErrorMessage);
    }

}