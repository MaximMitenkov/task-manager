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
        original.setUsername(request.username());
        original.setEmail(request.email());
        return original;
    }

    public User updatePassword(UserPasswordUpdateRequest request, User original) {
        original.setPassword(passwordEncoder.encode(request.password()));
        return original;
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
