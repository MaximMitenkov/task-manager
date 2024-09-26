package org.mitenkov.service.validator;

import org.mitenkov.dto.UserAddRequest;
import org.mitenkov.dto.UserUpdateRequest;
import org.mitenkov.entity.User;
import org.mitenkov.enums.ErrorCode;
import org.mitenkov.enums.UserRole;
import org.mitenkov.exception.ErrorCodeException;
import org.mitenkov.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserProvider {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User validateCurrentUser(UserUpdateRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User originalUser = userRepository.findByUsername(username);
        return validateUser(request, originalUser);
    }

    public User validateUser(UserUpdateRequest request) {
        User originalUser = provideIfPresent(request.id());
        return validateUser(request, originalUser);
    }

    public User validateUser(UserUpdateRequest request, User original) {
        int id = original.getId();
        String username;
        String password;
        String email;

        if (request.username() == null || request.username().isEmpty()) {
            username = original.getUsername();
        } else {
            username = provideIfUnique(request.username());
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

    public User validateUser(UserAddRequest request) {
        if (userRepository.findByUsername(request.username()) != null) {
            throw new ErrorCodeException(ErrorCode.VALIDATION_ERROR);
        }

        return User.builder()
                .role(UserRole.USER)
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .username(request.username())
                .isActive(true)
                .build();
    }

    public User blockUser(int id) {
        User original = provideIfPresent(id);
        original.setActive(!original.isActive());

        return original;
    }

    private String provideIfUnique(String username) {

        User original = userRepository.findByUsername(username);

        if (original == null) {
            return username;
        } else {
            throw new ErrorCodeException(ErrorCode.VALIDATION_ERROR);
        }
    }

    private User provideIfPresent(int id) {
        return Optional.of(userRepository.findUserById(id))
                .orElseThrow(() -> new ErrorCodeException(ErrorCode.NO_SUCH_ELEMENT));
    }
}
