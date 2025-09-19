package ru.bmstu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bmstu.entity.UserLocal;
import ru.bmstu.repository.UserRepository;
import ru.bmstu.service.UserService;

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
    public UserLocal updateUser(int id, int tokenDif){
        List<UserLocal> users = userRepository.findAll();
        UserLocal userLocal = users.stream().filter(x -> x.getId() == id).findFirst().orElseThrow(() -> new NoSuchElementException("UserLocal with id=" + id + " not found"));
        if (userLocal.getTokens() + tokenDif < 0) {
            throw new IllegalArgumentException("Resulting tokens can't be negative");
        }
        userLocal.setTokens(userLocal.getTokens() + tokenDif);
        userRepository.saveAllAndFlush(users);
        return userLocal;
    }

    @Override
    public UserLocal addUser(String fullName, String role, String password){
        if (fullName== null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (role == null || role.trim().isEmpty()) {
            throw new IllegalArgumentException("Role cannot be null or empty");
        }
        if (!role.equals("Student") && !role.equals("Teacher")) {
            throw new IllegalArgumentException("Role must be 'Student' or 'Teacher'");
        }
        UserLocal newUserLocal = new UserLocal(fullName, role, password, ((role.equals("Student"))?(10):(null)));
        userRepository.saveAndFlush(newUserLocal);
        return newUserLocal;
    }

    @Override
    public UserLocal deleteUser(int id){
        List<UserLocal> users = getUsers();
        UserLocal userLocal = users.stream().filter(x -> x.getId() == id).findFirst().orElseThrow(() -> new NoSuchElementException("User with id=" + id + " not found"));
        userLocal.setDeleted(true);
        userRepository.save(userLocal);
        return userLocal;
    }

    @Override
    public List<UserLocal> getUsers(){
        return userRepository.findAll().stream().filter(x-> !x.getDeleted()).toList();
    }
}
