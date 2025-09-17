package ru.bmstu.service;


import com.opencsv.exceptions.CsvValidationException;
import ru.bmstu.object.User;

import java.io.IOException;
import java.util.List;

public interface UserService {
    User updateUser(int id, int token);
    User addUser(String fullName, String role);
    User deleteUser(int id);
    List<User> getUsers();
}
