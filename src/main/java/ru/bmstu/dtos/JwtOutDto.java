package ru.bmstu.dtos;

public class JwtOutDto {
    private String token;

    public JwtOutDto() {

    }

    public JwtOutDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
