//package ru.bmstu.aspect;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import ru.bmstu.repository.UserRepository;
//
//@Aspect
//@Component
//public class RoleCheck {
//    private static String currentRole;
//    private final UserRepository userRepository;
//
//    @Autowired
//    public RoleCheck(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    public static String getCurrentRole() {
//        return currentRole;
//    }
//
//    public static boolean isTeacher(){
//        return currentRole.equals("Teacher");
//    }
//
//    public static void setCurrentRole(String currentRole) {
//        RoleCheck.currentRole = currentRole;
//    }
//
//    @Before("execution(* ru.bmstu.service.UserService.updateUser(..))")
//    public void checkAccessUpdateUser(JoinPoint joinPoint) {
//        Object[] args = joinPoint.getArgs();
//        int id = (int) args[0];
//        int tokenDif = (int) args[1];
//
//        if(tokenDif < 0) {
//            return;
//        }
//        if(currentRole.equals("Student")) {
//            throw new SecurityException("\"Only teachers can add tokens to students!\"");
//        }
//    }
//
//    @Before("execution(* ru.bmstu.service.UserService.deleteUser(..))")
//    public void checkAccessDeleteUser() {
//        if(currentRole.equals("Student")) {
//            throw new SecurityException("\"Only teachers can delete users!\"");
//        }
//    }

//}
