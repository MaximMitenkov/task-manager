package controller;

import entities.Bug;
import entities.Feature;
import entities.Priority;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskCreator {

    private final Scanner scanner;
    private final String regex = "[0-9]+\\.[0-9]+\\.[0-9]+";
    private final Pattern pattern = Pattern.compile(regex);

    public TaskCreator(Scanner scanner) {
        this.scanner = scanner;


    }

    public Feature buildFeature() {
        try {
            System.out.println("Enter Title of the task");
            String title = scanner.nextLine();
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
            return buildFeature();
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Try again.");
            return buildFeature();
        }
    }

    public Bug buildBug() {
        try {
            System.out.println("Enter Title of the task");
            String title = scanner.nextLine();
            System.out.println("Enter Priority of the task");
            Priority priority = Priority.valueOf(scanner.nextLine().toUpperCase());
            System.out.println("Enter Deadline of the task in yyyy-MM-dd format");
            LocalDate deadLine = LocalDate.parse(scanner.nextLine());
            System.out.println("Enter Version in number.number.number format");
            String version = scanner.nextLine();
            Matcher matcher = pattern.matcher(version);
            if (!matcher.matches()) {
                throw new InputMismatchException();
            }
            return Bug.builder()
                    .title(title)
                    .priority(priority)
                    .deadline(deadLine)
                    .version(version)
                    .build();
        } catch (IllegalArgumentException e) {
            System.out.println("Unknown priority type. Try again.");
            return buildBug();
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Try again.");
            return buildBug();
        } catch (InputMismatchException e) {
            System.out.println("Invalid version format. Try again.");
            return buildBug();
        }
    }
}
