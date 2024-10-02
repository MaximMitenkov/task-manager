package org.mitenkov.Authintication;

import lombok.Builder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Builder
public class AuthHolder {
    public static AuthInfo getCurrentUser() {
        return (AuthInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
