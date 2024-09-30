package org.mitenkov.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserPasswordUpdateRequest(
        Integer id,

        @NotBlank
        @Size(max = 20)
        String password
) {
}
