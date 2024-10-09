package org.mitenkov.Authintication;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.mitenkov.entity.User;
import org.mitenkov.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class AuthInfo implements UserDetails {

    @Getter
    private final int id;

    @Getter
    private final String email;

    private final String username;

    private final String password;

    private final UserRole role;

    public AuthInfo(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.id = user.getId();
        this.email = user.getEmail();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(role);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
