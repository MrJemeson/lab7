package ru.bmstu.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import ru.bmstu.dtos.ErrorDto;
import ru.bmstu.exception.CustomBadCredentialsException;
import ru.bmstu.exception.CustomIllegalAccessException;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDto> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String paramName = e.getName();
        String value = (e.getValue() != null) ? e.getValue().toString() : "null";
        String message = String.format("Invalid value '%s' for parameter '%s'", value, paramName);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), message));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDto> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDto> handleEmptyBody(HttpMessageNotReadableException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), "Request body is required"));
    }

    @ExceptionHandler(CustomIllegalAccessException.class)
    public ResponseEntity<ErrorDto> handleCustomIllegalAccessException(CustomIllegalAccessException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorDto(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase(), e.getMessage()));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorDto> handleNoSuchElementException(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), e.getMessage()));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorDto> handleNoHandlerFound(NoHandlerFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), "Endpoint not found"));
    }

    @ExceptionHandler(CustomBadCredentialsException.class)
    public ResponseEntity<ErrorDto> handleCustomBadCredentialsException(CustomBadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorDto(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), "Invalid username or password"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGenericException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "Unexpected error occurred"));
    }
}