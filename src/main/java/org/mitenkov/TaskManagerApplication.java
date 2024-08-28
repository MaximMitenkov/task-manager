package org.mitenkov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class TaskManagerApplication {
    public static void main(String[] args) {
        try {
            SpringApplication.run(TaskManagerApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
