package org.mitenkov.service.provider;

import org.mitenkov.dto.UserAddRequest;
import org.mitenkov.dto.UserUpdateRequest;
import org.mitenkov.entity.User;
import org.mitenkov.enums.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserProvider {

    PasswordEncoder passwordEncoder;

    public User updateUser(UserUpdateRequest request, User original) {
        int id = original.getId();
        String username;
        String password;
        String email;

        if (request.username() == null || request.username().isEmpty()) {
            username = original.getUsername();
        } else {
            username = request.username();
        }

        if (request.password() == null || request.password().isEmpty() || request.password().isBlank()) {
            password = original.getPassword();
        } else {
            password = passwordEncoder.encode(request.password());
        }

        if (request.email() == null || request.email().isEmpty() || request.email().isBlank()) {
            email = null;
        } else {
            email = request.email();
        }

        return User.builder()
                .id(id)
                .username(username)
                .password(password)
                .email(email)
                .isActive(original.isActive())
                .role(original.getRole())
                .build();
    }

    public User createUser(UserAddRequest request) {
        return User.builder()
                .role(UserRole.USER)
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .username(request.username())
                .isActive(true)
                .build();
    }
}
