package org.mitenkov;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public abstract class BaseTest {
    static PostgreSQLContainer<?> postrges = new PostgreSQLContainer<>("postgres:16.4-alpine");

    @BeforeAll
    public static void beforeAll() {
        if (!postrges.isRunning()) {
            postrges.start();
        }
    }

    @DynamicPropertySource
    public static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postrges::getJdbcUrl);
        registry.add("spring.datasource.username", postrges::getUsername);
        registry.add("spring.datasource.password", postrges::getPassword);
    }
}
