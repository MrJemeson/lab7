package ru.bmstu.exception;

public class CustomIllegalAccessException extends RuntimeException {
    public CustomIllegalAccessException(String message) {
        super(message);
    }
}
