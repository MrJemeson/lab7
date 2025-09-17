package ru.bmstu.service.impl;

import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bmstu.object.User;
import ru.bmstu.repository.UserRepository;
import ru.bmstu.service.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User updateUser(int id, int tokenDif){
        List<User> users = userRepository.loadUsers();
        User user = users.stream().filter(x -> x.getID() == id).findFirst().orElseThrow(() -> new NoSuchElementException("User with id=" + id + " not found"));
        if (user.getTokens() + tokenDif < 0) {
            throw new IllegalArgumentException("Resulting tokens can't be negative");
        }
        user.setTokens(user.getTokens() + tokenDif);
        userRepository.saveUsers(users);
        return user;
    }

    @Override
    public User addUser(String fullName, String role){
        if (fullName== null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (role == null || role.trim().isEmpty()) {
            throw new IllegalArgumentException("Role cannot be null or empty");
        }
        if (!role.equals("Student") && !role.equals("Teacher")) {
            throw new IllegalArgumentException("Role must be 'Student' or 'Teacher'");
        }
        List<User> users = userRepository.loadUsers();
        User newUser = new User(users.stream().mapToInt(User::getID).max().orElse(0)+1, fullName, role, ((role.equals("Student"))?(10):(null)));
        users.add(newUser);
        userRepository.saveUsers(users);
        return newUser;
    }

    @Override
    public User deleteUser(int id){
        List<User> users = userRepository.loadUsers();
        User user = users.stream().filter(x -> x.getID() == id).findFirst().orElseThrow(() -> new NoSuchElementException("User with id=" + id + " not found"));
        users.remove(user);
        userRepository.saveUsers(users);
        return user;
    }

    @Override
    public List<User> getUsers(){
        return userRepository.loadUsers();
    }
}
