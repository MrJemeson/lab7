package ru.bmstu.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bmstu.entity.UserLocal;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserLocal, Integer> {
    Optional<UserLocal> findByFullName(String full_name);
}