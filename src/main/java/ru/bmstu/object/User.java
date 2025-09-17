package ru.bmstu.object;

public class User {
    private final int ID;
    private final String FULL_NAME;
    private final String ROLE;
    private Integer tokens;

    public User(int id, String fullName, String role, Integer tokens) {
        this.ID = id;
        this.FULL_NAME = fullName;
        this.ROLE = role;
        this.tokens = tokens;
    }


    public int getID() {
        return ID;
    }
    public String getFULL_NAME() {
        return FULL_NAME;
    }
    public String getROLE(){return ROLE;}
    public Integer getTokens() {
        return tokens;
    }
    public void setTokens(int tokens) {
        this.tokens = tokens;
    }

    @Override
    public String toString() {
        return "ID:"+ this.getID() + " "  + this.getFULL_NAME() + ". " + this.getROLE() +
        ((this.getROLE().equals("Student"))?(". Tokens: " + this.getTokens()):("."));
    }
}
