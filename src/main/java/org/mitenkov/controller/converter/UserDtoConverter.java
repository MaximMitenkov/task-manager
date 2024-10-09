package org.mitenkov.controller.converter;

import org.mitenkov.dto.UserDto;
import org.mitenkov.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter {
    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .isActive(user.isActive())
                .build();
    }
}
