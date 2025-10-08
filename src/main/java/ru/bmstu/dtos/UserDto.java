package ru.bmstu.dtos;

public class UserDto {
    private int id;
    private String fullName;
    private String role;
    private int tokens;

    public UserDto(int id, String fullName, String role, int tokens) {
        this.id = id;
        this.fullName = fullName;
        this.role = role;
        this.tokens = tokens;
    }

    public int getId() { return id; }
    public String getFullName() { return fullName; }
    public String getRole() { return role; }
    public int getTokens() { return tokens; }
}

