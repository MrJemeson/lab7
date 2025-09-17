package ru.bmstu.aspect;

import com.opencsv.exceptions.CsvValidationException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bmstu.object.User;
import ru.bmstu.repository.UserRepository;

import java.io.IOException;
import java.util.List;

@Aspect
@Component
public class AccessAspect {
    private List<User> teachers;
    private static String currentCredentials;

    public static void setCurrentCredentials(String credentials) {
        currentCredentials = credentials;
    }

    @Autowired
    public AccessAspect(UserRepository repository) throws CsvValidationException, IOException {
        teachers = repository.loadUsers().stream().filter(x -> x.getROLE().equals("Teacher")).toList();
    }

    public boolean defineRole() {
        try {
            for (User teacher : teachers) {
                if (teacher.getFULL_NAME().equals(currentCredentials)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            throw new IllegalArgumentException("Incorrect header format");
        }
    }

    @Before("execution(* ru.bmstu.service.UserService.deleteUser(..))")
    public void checkAccessDeleteUser() throws Throwable {
        if (!defineRole()) {
            throw new SecurityException("Only teachers can perform this action!");
        }
    }

    @Before("execution(* ru.bmstu.service.UserService.addUser(..))")
    public void checkAccessAddUser(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String fullName = (String) args[0];
        String role = (String) args[1];

        if(!role.equals("Teacher")) {
            return;
        }
        if (!defineRole()) {
            throw new SecurityException("Only teachers can perform this action!");
        }

    }

    @Before("execution(* ru.bmstu.service.UserService.updateUser(..))")
    public void checkAccessUpdateUser(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        int id = (int) args[0];
        int tokenDif = (int) args[1];

        if(tokenDif < 0) {
            return;
        }
        if (!defineRole()) {
            throw new SecurityException("Only teachers can perform this action!");
        }

    }
}
