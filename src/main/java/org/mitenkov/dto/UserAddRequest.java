package org.mitenkov.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserAddRequest(

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
