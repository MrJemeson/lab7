package ru.bmstu.dtos;

public class ErrorDto {
    private int status;            // HTTP-код (например, 404, 400, 403)
    private String error;          // Текстовое название статуса (например, "NOT_FOUND")
    private String message;        // Человеческое описание ошибки

    public ErrorDto() {
    }

    public ErrorDto(int status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
