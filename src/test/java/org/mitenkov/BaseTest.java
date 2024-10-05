package org.mitenkov;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@RequiredArgsConstructor
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

    public Set<?> getSet(List<?> list) {
        return new HashSet<>(list);
    }
}
