package ru.bmstu.mapper;

import org.springframework.stereotype.Component;
import ru.bmstu.dtos.UserDto;
import ru.bmstu.entity.UserLocal;

@Component
public class UserMapper {

    public UserDto toDto(UserLocal entity) {
        if (entity == null) return null;
        return new UserDto(
                entity.getId(),
                entity.getFullName(),
                entity.getRole(),
                entity.getTokens()
        );
    }
}