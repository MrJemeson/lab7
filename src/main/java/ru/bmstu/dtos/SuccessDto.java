package ru.bmstu.dtos;

public class SuccessDto {
    private String message;

    public SuccessDto() {
    }

    public SuccessDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
