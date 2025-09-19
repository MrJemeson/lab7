package ru.bmstu.service;


import jakarta.transaction.Transactional;
import ru.bmstu.entity.UserLocal;

import java.util.List;

public interface UserService {
    @Transactional
    UserLocal updateUser(int id, int token);
    @Transactional
    UserLocal addUser(String fullName, String role, String password);
    @Transactional
    UserLocal deleteUser(int id);
    @Transactional
    List<UserLocal> getUsers();
}
