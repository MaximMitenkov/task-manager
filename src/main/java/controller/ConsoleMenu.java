package controller;

import dao.FilterType;
import dao.SortType;
import entities.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import service.Manager;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.PatternSyntaxException;

@Slf4j
@RequiredArgsConstructor
public class ConsoleMenu {

    final private Manager manager;

    final Scanner scanner = new Scanner(System.in);

    public void startMenu() {
        log.info("Menu started");
        while (true) {
            System.out.println("""
                WELCOME TO THE MENU
                1) Show tasks
                2) Show sorted tasks
                3) Add a new task
                4) Remove a task
                
                0) Exit
                """);

            try {
                switch (Integer.parseInt(scanner.nextLine())) {
                    case 1 -> filter();
                    case 2 -> sort();
                    case 3 -> addTasks();
                    case 4 -> removeTasks();
                    case 0 -> System.exit(0);
                    default -> {
                        System.out.println("You picked wrong number. Try again.");
                        log.warn("User entered wrong number");
                    }
                }
            } catch (NumberFormatException numberFormatException) {
                log.error("User entered not a number", numberFormatException);
                System.out.println("You should enter a valid number. Try again.");
                scanner.nextLine();
            }
        }
    }

    private void filter() {
        log.info("Filter menu opened");
        System.out.println("""
            Enter the type of filter:
            1) Show all tasks
            2) Show only bugs
            3) Show only features
            """);
        try {
            switch (Integer.parseInt(scanner.nextLine())) {
                case 1 -> showTasks();
                case 2 -> showTasks(FilterType.BUG);
                case 3 -> showTasks(FilterType.FEATURE);
                default -> {
                    System.out.println("You picked wrong number. Try again.");
                    log.warn("User picked wrong number in filter menu");
                }
            }
        } catch (NumberFormatException numberFormatException) {
            log.error("User entered not a number in filter menu", numberFormatException);
            System.out.println("You should enter a valid number. Try again.");
        } finally {
            scanner.nextLine();
        }
    }

    private void sort() {
        log.info("Sort menu opened");
        System.out.println("""
                Enter the type of sort method:
                1) Title sort
                2) Priority sort
                3) Deadline sort
                """);
        try {
            switch (Integer.parseInt(scanner.nextLine())) {
                case 1 -> showTasks(SortType.TITLE);
                case 2 -> showTasks(SortType.PRIORITY);
                case 3 -> showTasks(SortType.DEADLINE);
                default -> {
                    System.out.println("You entered wrong number. Try again.");
                    log.warn("User entered wrong number in sort menu");
                }
            }
        } catch (NumberFormatException numberFormatException) {
            log.error("User entered not a number in sort menu", numberFormatException);
            System.out.println("You should enter a valid number. Try again.");
        } finally {
            scanner.nextLine();
        }
    }

    private void showTasks() {
        log.info("Show tasks");
        var tasks = manager.getTasks();
        if (tasks.isEmpty()) {
            log.warn("User have no tasks");
            System.out.println("You have no tasks.");
            scanner.nextLine();
            return;
        }
        for (Task task : tasks) {
            System.out.println(task.toString());
        }
    }

    private void showTasks(SortType type) {
        log.info("Show sorted tasks by {}", type);
        var tasks = manager.getSortedTasks(type);
        if (tasks.isEmpty()) {
            System.out.println("You have no tasks.");
            log.warn("User have no tasks of {}", type);
            scanner.nextLine();
            return;
        }
        for (Task task : tasks) {
            System.out.println(task.toString());
        }
    }

    private void showTasks(FilterType type) {
        log.info("Show filtered tasks by {}", type);
        var tasks = manager.getFilteredTasks(type);
        if (tasks.isEmpty()) {
            System.out.println("You have no tasks of this type.");
            log.warn("User have no tasks of this type.");
            scanner.nextLine();
            return;
        }
        for (Task task : tasks) {
            System.out.println(task.toString());
        }
    }

    private void addTasks() {
        log.info("Adding task menu opened");
        System.out.println("""
            Enter the type of the task you would like to add:
            1) Add a new Feature;
            2) Add a new Bug;
         """);

        TaskCreator taskCreator = new TaskCreator(scanner);
        try {
            switch (Integer.parseInt(scanner.nextLine())) {
                case 1 -> {
                    manager.addTask(taskCreator.buildFeature());
                    log.info("Feature created");
                    System.out.println("Feature added successfully.");
                }
                case 2 -> {
                    manager.addTask(taskCreator.buildBug());
                    log.info("Bug created");
                    System.out.println("Bug added successfully.");
                }
                default -> {
                    System.out.println("You picked wrong number. Try again.");
                    log.warn("User picked wrong number in add task menu");
                }
            }
            scanner.nextLine();
        } catch (NumberFormatException numberFormatException) {
            log.error("User entered not a number", numberFormatException);
            System.out.println("You should enter a valid number. Try again.");
        } catch (PatternSyntaxException e) {
            log.error("User entered version in incorrect format", e);
            System.out.println("Incorrect format of version. Try again.");
            manager.addTask(taskCreator.buildBug());
        } finally {
            scanner.nextLine();
        }
    }

    private void removeTasks() {
        log.info("Removing task menu opened");
        try {
            ArrayList<Task> tasks = manager.getTasks();
            if (tasks.isEmpty()) {
                System.out.println("You have no tasks to remove.");
                log.warn("User have no tasks to remove.");
                scanner.nextLine();
                return;
            }
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i+1) + ") " + tasks.get(i).toString());
            }
            System.out.println("\nEnter number of task you want to remove: ");
            manager.removeTask(tasks.get(Integer.parseInt(scanner.nextLine()) - 1));
            log.info("Task removed successfully.");
            System.out.println("Task removed successfully.");
            scanner.nextLine();
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            System.out.println("You entered wrong number. Try again.");
            log.warn("User entered wrong number in remove task menu");
            scanner.nextLine();
        } catch (NumberFormatException numberFormatException) {
            System.out.println("You should enter a valid number. Try again.");
            log.error("User entered not a number in remove menu", numberFormatException);
            scanner.nextLine();
        }
    }

}


