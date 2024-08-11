package org.mitenkov.controller;

import org.mitenkov.entities.Bug;
import org.mitenkov.entities.Feature;
import org.mitenkov.entities.Priority;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

@Slf4j
@RequiredArgsConstructor
public class TaskCreator {

    private final Scanner scanner;

    public Feature buildFeature() {
        try {
            log.debug("Start building feature");
            System.out.println("Enter Title of the task");
            String title = firstLetterToUpperCase(scanner.nextLine());
            System.out.println("Enter Priority of the task");
            Priority priority = Priority.valueOf(scanner.nextLine().toUpperCase());
            System.out.println("Enter Deadline of the task in yyyy-MM-dd format");
            LocalDate deadLine = LocalDate.parse(scanner.nextLine());
            return Feature.builder()
                    .title(title)
                    .priority(priority)
                    .deadline(deadLine)
                    .build();
        } catch (IllegalArgumentException e) {
            System.out.println("Unknown priority type. Try again.");
            log.error("User entered unknown priority type", e);
            scanner.nextLine();
            return buildFeature();
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Try again.");
            log.error("User entered Invalid date format", e);
            scanner.nextLine();
            return buildFeature();
        }
    }

    public Bug buildBug() {
        try {
            log.debug("Start building bug");
            System.out.println("Enter Title of the task");
            String title = firstLetterToUpperCase(scanner.nextLine());
            System.out.println("Enter Priority of the task");
            Priority priority = Priority.valueOf(scanner.nextLine().toUpperCase());
            System.out.println("Enter Deadline of the task in yyyy-MM-dd format");
            LocalDate deadLine = LocalDate.parse(scanner.nextLine());
            System.out.println("Enter Version in number.number.number format");
            String version = scanner.nextLine();
            return Bug.builder()
                    .title(title)
                    .priority(priority)
                    .deadline(deadLine)
                    .version(version)
                    .build();
        } catch (IllegalArgumentException e) {
            System.out.println("Unknown priority type. Try again.");
            log.error("User entered unknown priority type", e);
            scanner.nextLine();
            return buildBug();
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Try again.");
            log.error("User entered Invalid date format", e);
            scanner.nextLine();
            return buildBug();
        }
    }

    private String firstLetterToUpperCase(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }
}
