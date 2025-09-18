package ru.bmstu.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int ID;

    @Column(name = "full_name", nullable = false)
    private String FULL_NAME;

    @Column(name = "role", nullable = false)
    private String ROLE;

    @Column(name = "tokens")
    private Integer tokens;

    public User() {}

    public User(String fullName, String role, Integer tokens) {
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
