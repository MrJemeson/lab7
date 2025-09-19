package ru.bmstu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bmstu.entity.UserLocal;
import ru.bmstu.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceSec implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceSec(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        UserLocal userLocal = userRepository.findByFullName(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' not found", username)
        ));
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+userLocal.getRole().toUpperCase()));
        User user = new User(userLocal.getFullName(), userLocal.getPassword(), authorities);
        return user;
    }
}