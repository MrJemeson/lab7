package ru.bmstu.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bmstu.service.LoggerService;

@Aspect
@Component
public class LoggerAspect {
    private final LoggerService loggerService;

    @Autowired
    public LoggerAspect(LoggerService loggerService) {
        this.loggerService = loggerService;
    }

    @AfterReturning(pointcut = "execution(* ru.bmstu.service.UserService.updateUser(..)) || " +
            "execution(* ru.bmstu.service.UserService.addUser(..)) || " +
            "execution(* ru.bmstu.service.UserService.deleteUser(..))",
            returning = "result")
    public void makeLog(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        String logMessage = methodName + " - " + result.toString();
        loggerService.log(logMessage);
    }
}
