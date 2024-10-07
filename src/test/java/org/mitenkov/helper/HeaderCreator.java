package org.mitenkov.helper;

import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class HeaderCreator {

    public String createBasicAuthHeader(String username, String password) {
        String auth = username + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        return "Basic " + encodedAuth;
    }
}
