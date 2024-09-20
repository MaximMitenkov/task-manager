package org.mitenkov.dto;

import jakarta.validation.constraints.Email;

public record UserDto(
        Integer id,
        String username,

        @Email
        String email,
        String password
) {
}
