package ru.bmstu.dtos;

public class UserDto {
    private int id;
    private String fullName;
    private String role;
    private Integer tokens;

    public UserDto(int id, String fullName, String role, Integer tokens) {
        this.id = id;
        this.fullName = fullName;
        this.role = role;
        this.tokens = tokens;
    }

    public int getId() { return id; }
    public String getFullName() { return fullName; }
    public String getRole() { return role; }
    public Integer getTokens() { return tokens; }
}

