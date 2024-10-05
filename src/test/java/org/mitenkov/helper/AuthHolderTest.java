package org.mitenkov.helper;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class AuthHolderTest {

    @Value("${app.admin.username}")
    private final String adminUsername;

    @Value("${app.admin.password}")
    private final String adminPassword;

    public static String defaultUsername = "TestUsername";

    public static String defaultPassword = "1234";

    public static String currentUsername = "TestUsername";

    public static String currentPassword = "1234";

}
