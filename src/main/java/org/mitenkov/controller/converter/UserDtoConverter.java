package org.mitenkov.controller.converter;

import lombok.RequiredArgsConstructor;
import org.mitenkov.dto.UserDto;
import org.mitenkov.entity.User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDtoConverter {
    public UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }
}
