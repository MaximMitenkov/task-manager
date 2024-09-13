package org.mitenkov.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Task manager")
                .version("1.0")
                .description("My pet application to learn Spring")
                .summary("Pet project");

        return new OpenAPI().info(info);
    }

}
