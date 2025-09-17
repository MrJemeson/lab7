package ru.bmstu.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import ru.bmstu.aspect.AccessAspect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CredentialsInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        if (request.getRequestURI().contains("/users") && !request.getMethod().equals("GET")) {
            String credentials = request.getHeader("User-Credentials");
            if (credentials == null || credentials.trim().isEmpty()) {
                throw new IllegalArgumentException("User-Credentials header is required");
            }

            AccessAspect.setCurrentCredentials(credentials);

            return true;
        }
        return true;
    }
}