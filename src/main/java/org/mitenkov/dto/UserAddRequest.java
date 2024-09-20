package org.mitenkov.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserAddRequest(
        Integer id,

        @NotBlank
        @Size(max = 50)
        String username,

        @Email
        @Size(max = 50)
        String email,

        @NotBlank
        @Size(max = 20)
        String password
) {
}
