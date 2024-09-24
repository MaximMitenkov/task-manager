package org.mitenkov.service;

import lombok.RequiredArgsConstructor;
import org.mitenkov.dto.UserAddRequest;
import org.mitenkov.entity.User;
import org.mitenkov.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public User saveUser(UserAddRequest request) {
        return userRepository.save(User.builder()
                .email(request.email())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .build());
    }

    public User updateUser(UserAddRequest request) {

        return userRepository.save(User.builder()
                .email(request.email())
                .username(request.username())
                .password(request.password())
                .build());
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
                return username;
            }
        };
    }
}
