package org.mitenkov.service.provider;

import lombok.RequiredArgsConstructor;
import org.mitenkov.dto.UserAddRequest;
import org.mitenkov.dto.UserPasswordUpdateRequest;
import org.mitenkov.dto.UserUpdateRequest;
import org.mitenkov.entity.User;
import org.mitenkov.enums.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserProvider {

    private final PasswordEncoder passwordEncoder;

    public User updateUser(UserUpdateRequest request, User original) {
        int id = original.getId();
        String email;

        String username = request.username() == null || request.username().isEmpty()
                ? original.getUsername()
                : request.username();


        if (request.email() == null || request.email().isBlank()) {
            email = null;
        } else {
            email = request.email();
        }

        return User.builder()
                .id(id)
                .username(username)
                .password(original.getPassword())
                .email(email)
                .isActive(original.isActive())
                .role(original.getRole())
                .build();
    }

    public User updatePassword(UserPasswordUpdateRequest request, User original) {
        return User.builder()
                .id(original.getId())
                .username(original.getUsername())
                .password(request.password())
                .email(original.getEmail())
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
