package ru.bmstu.repository;


import com.opencsv.exceptions.CsvValidationException;
import ru.bmstu.object.User;

import java.io.IOException;
import java.util.List;

public interface UserRepository {
    List<User> loadUsers();
    User parseCsvLine(String[] parts);
    void saveUsers(List<User> users);
    String toCsvLine(User user);
}
