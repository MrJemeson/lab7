package ru.bmstu.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserLocal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "tokens")
    private Integer tokens;

    @Column(name = "isDeleted", nullable = false)
    private Boolean isDeleted;

    public UserLocal() {}

    public UserLocal(String fullName, String role, Integer tokens) {
        this.fullName = fullName;
        this.role = role;
        this.tokens = tokens;
        isDeleted = false;
    }

    public UserLocal(String fullName, String role, String password, Integer tokens) {
        this.fullName = fullName;
        this.role = role;
        this.password = password;
        this.tokens = tokens;
        isDeleted = false;
    }


    public int getId() {
        return id;
    }
    public String getFullName() {
        return fullName;
    }
    public String getRole(){return role;}
    public Integer getTokens() {
        return tokens;
    }
    public void setTokens(int tokens) {
        this.tokens = tokens;
    }
    public String getPassword() {
        return password;
    }
    public Boolean getDeleted() {
        return isDeleted;
    }
    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "ID:"+ this.getId() + " "  + this.getFullName() + ". " + this.getRole() +
        ((this.getRole().equals("Student"))?(". Tokens: " + this.getTokens()):("."));
    }
}
