package ru.bmstu.service;


import jakarta.transaction.Transactional;
import ru.bmstu.entity.User;

import java.util.List;

public interface UserService {
    @Transactional
    User updateUser(int id, int token);
    @Transactional
    User addUser(String fullName, String role);
    @Transactional
    User deleteUser(int id);
    @Transactional
    List<User> getUsers();
}
