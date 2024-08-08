package controller;

import entities.Bug;
import entities.Feature;
import entities.Priority;

import java.time.LocalDate;
import java.util.Scanner;

public class TaskCreator {

    Scanner scanner;

    public TaskCreator(Scanner scanner) {
        this.scanner = scanner;
    }

    public Feature buildFeature() {
        System.out.println("Enter Title of the task");
        String title = scanner.nextLine();
        System.out.println("Enter Priority of the task");
        Priority priority = Priority.valueOf(scanner.nextLine());
        System.out.println("Enter Deadline of the task");
        LocalDate deadLine = LocalDate.parse(scanner.nextLine());
        return Feature.builder()
                .title(title)
                .priority(priority)
                .deadline(deadLine)
                .build();
    }

    public Bug buildBug() {
        System.out.println("Enter Title of the task");
        String title = scanner.nextLine();
        System.out.println("Enter Priority of the task");
        Priority priority = Priority.valueOf(scanner.nextLine());
        System.out.println("Enter Deadline of the task");
        LocalDate deadLine = LocalDate.parse(scanner.nextLine());
        System.out.println("Enter Version");
        int version = scanner.nextInt();
        return Bug.builder()
                .title(title)
                .priority(priority)
                .deadline(deadLine)
                .version(version)
                .build();
    }
}
