package org.mitenkov.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mitenkov.dto.UserAddRequest;
import org.mitenkov.dto.UserUpdateRequest;
import org.mitenkov.entity.User;
import org.mitenkov.enums.ErrorCode;
import org.mitenkov.exception.ErrorCodeException;
import org.mitenkov.repository.UserRepository;
import org.mitenkov.service.provider.UserProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class UserService implements UserDetailsService {


    private final UserRepository userRepository;
    private final UserProvider userProvider;

    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public User saveUser(@Valid UserAddRequest request) {
        if (userRepository.findByUsername(request.username()) != null) {
            throw new ErrorCodeException(ErrorCode.VALIDATION_ERROR);
        }
        return userRepository.save(userProvider.createUser(request));
    }

    public User updateCurrentUser(@Valid UserUpdateRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        return userRepository.save(userProvider.updateUser(request, user));
    }

    public User updateUser(@Valid UserUpdateRequest request) {
        User originalUser = getIfPresent(request.id());
        if (request.username() != null) {
            validateUnique(request.username());
        }
        return userRepository.save(userProvider.updateUser(request, originalUser));
    }

    public User blockUser(int id) {
        User user = getIfPresent(id);
        user.setActive(!user.isActive());
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        return new UserDetails() {

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of(user.getRole());
            }

            @Override
            public String getPassword() {
                return user.getPassword();
            }

            @Override
            public String getUsername() {
                return user.getUsername();
            }
        };
    }

    private User getIfPresent(int id) {
        return Optional.of(userRepository.findUserById(id))
                .orElseThrow(() -> new ErrorCodeException(ErrorCode.NO_SUCH_ELEMENT));
    }

    private void validateUnique(String username) {
        User original = userRepository.findByUsername(username);
        if (original != null) {
            throw new ErrorCodeException(ErrorCode.VALIDATION_ERROR);
        }
    }
}
