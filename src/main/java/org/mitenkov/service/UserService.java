package org.mitenkov.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mitenkov.Authintication.AuthHolder;
import org.mitenkov.Authintication.AuthInfo;
import org.mitenkov.dto.UserAddRequest;
import org.mitenkov.dto.UserPasswordUpdateRequest;
import org.mitenkov.dto.UserUpdateRequest;
import org.mitenkov.entity.User;
import org.mitenkov.enums.ErrorCode;
import org.mitenkov.enums.UserRole;
import org.mitenkov.exception.ErrorCodeException;
import org.mitenkov.repository.UserRepository;
import org.mitenkov.service.provider.UserProvider;
import org.mitenkov.service.validator.UserValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Service
@Validated
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserProvider userProvider;
    private final UserValidator userValidator;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.username}")
    private final String adminUsername;

    @Value("${app.admin.password}")
    private final String adminPassword;

    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public User saveUser(@Valid UserAddRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new ErrorCodeException(ErrorCode.NOT_UNIQUE);
        }
        return userRepository.save(userProvider.createUser(request));
    }

    @Transactional
    public User updateCurrentUser(@Valid UserUpdateRequest request) {
        User originalUser = getByIdOrElseThrow(request.id());
        if (!Objects.equals(originalUser.getUsername(), AuthHolder.getCurrentUser().getUsername())) {
            throw new ErrorCodeException(ErrorCode.NOT_CURRENT_USER);
        }
        User result = userProvider.updateUser(request, originalUser);
        return userRepository.save(result);
    }

    @Transactional
    public User updateCurrentPassword(@Valid UserPasswordUpdateRequest request) {
        User originalUser = getByIdOrElseThrow(request.id());
        if (!Objects.equals(originalUser.getUsername(), AuthHolder.getCurrentUser().getUsername())) {
            throw new ErrorCodeException(ErrorCode.NOT_CURRENT_USER);
        }
        User result = userProvider.updatePassword(request, originalUser);
        return userRepository.save(result);
    }

    @Transactional
    public User updateUser(@Valid UserUpdateRequest request) {
        User originalUser = getByIdOrElseThrow(request.id());
        userValidator.validateUnique(request.username());
        User result = userProvider.updateUser(request, originalUser);
        return userRepository.save(result);
    }

    @Transactional
    public User updatePassword(@Valid UserPasswordUpdateRequest request) {
        User originalUser = getByIdOrElseThrow(request.id());
        User result = userProvider.updatePassword(request, originalUser);
        return userRepository.save(result);
    }

    @Transactional
    public User blockUser(int id) {
        User user = getByIdOrElseThrow(id);
        user.setActive(false);
        return userRepository.save(user);
    }

    @Transactional
    public User unblockUser(int id) {
        User user = getByIdOrElseThrow(id);
        user.setActive(true);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (!user.isActive()) {
            throw new ErrorCodeException(ErrorCode.ACCESS_DENIED);
        }
        return new AuthInfo(user);
    }

    private User getByIdOrElseThrow(int id) {
        User user = userRepository.findUserById(id);
        if (user == null) {
            throw new ErrorCodeException(ErrorCode.NO_SUCH_ELEMENT);
        }
        return user;
    }

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    protected void createInitAdminUser() {
        if (userRepository.findByRole(UserRole.ADMIN).isEmpty()) {
            addAdmin();
        }
    }

    private void addAdmin() {
        userRepository.save(User.builder()
                .role(UserRole.ADMIN)
                .username(adminUsername)
                .isActive(true)
                .password(passwordEncoder.encode(adminPassword))
                .build());
    }
}
