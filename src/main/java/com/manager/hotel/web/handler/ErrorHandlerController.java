package com.manager.hotel.web.handler;

import com.manager.hotel.exception.GuestNotFoundException;
import com.manager.hotel.exception.NoAvailableRoomsException;
import com.manager.hotel.exception.RoomNotFoundException;
import jakarta.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@ControllerAdvice
public class ErrorHandlerController extends ResponseEntityExceptionHandler {

    @ResponseStatus(value = CONFLICT, reason = "Data integrity violation")
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> conflict(
            final DataIntegrityViolationException ex) {
        return buildErrorResponse(CONFLICT,
                ex.getMessage());
    }

    @ResponseStatus(NOT_FOUND)
        @ExceptionHandler({GuestNotFoundException.class,
                RoomNotFoundException.class})
    public ResponseEntity<Object> handleGuestNotFoundException(
            GuestNotFoundException ex) {
        return buildErrorResponse(NOT_FOUND,
                ex.getMessage());
    }
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(NoAvailableRoomsException.class)
    public ResponseEntity<Object> handleNoAvailableRoomsException(
            NoAvailableRoomsException ex) {
        return buildErrorResponse(BAD_REQUEST,
                ex.getMessage());
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<Object> handlePersistenceException(
            PersistenceException ex) {
        log.error(ex.getMessage());
        return buildErrorResponse(INTERNAL_SERVER_ERROR,
                "An internal error occurred.");
    }

    private static ResponseEntity<Object> buildErrorResponse(
            HttpStatus error, String message) {
        return new ResponseEntity<>(
                new ErrorResponse(error, message),
                error);
    }

    @ResponseStatus(METHOD_NOT_ALLOWED)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleAllUncaughtException(
            final RuntimeException ex) {
        log.error(ex.getMessage());
        return buildErrorResponse(
                METHOD_NOT_ALLOWED,
                "Method Not Allowed");
    }
}
