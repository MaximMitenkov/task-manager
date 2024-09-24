package org.mitenkov.enums;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    USER, ADMIN;

    @Override
    public String getAuthority() {
        return this.name();
    }

    public static class Values {
        public static final String USER = "USER";
        public static final String ADMIN = "ADMIN";
    }
}
