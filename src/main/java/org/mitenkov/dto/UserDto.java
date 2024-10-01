package org.mitenkov.dto;

import lombok.Builder;

@Builder
public record UserDto(
        int id,
        String username,
        String email,
        Boolean isActive
) {
}
