package org.mitenkov.dto;

public record UserDto(
        int id,
        String username,
        String email,
        Boolean isActive
) {
}
