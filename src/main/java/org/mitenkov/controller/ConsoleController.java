package org.mitenkov.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mitenkov.controller.creator.CommentConsoleCreator;
import org.mitenkov.controller.creator.TaskConsoleCreator;
import org.mitenkov.entity.Comment;
import org.mitenkov.entity.Task;
import org.mitenkov.enums.FilterType;
import org.mitenkov.enums.SortType;
import org.mitenkov.service.CommentService;
import org.mitenkov.service.TaskService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.PatternSyntaxException;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ConsoleController {

    private final TaskService taskService;

    private final CommentService commentService;

    final Scanner scanner = new Scanner(System.in);

    @EventListener(ApplicationReadyEvent.class)
    public void startMenu() {
        log.info("Menu started");
        while (true) {
            System.out.println("""
                WELCOME TO THE MENU
                1) Show tasks
                2) Show sorted tasks
                3) Add a new task
                4) Remove a task
                5) Add a new comment
                6) Show comments of the author
                7) Check comments to the task
                
                0) Exit
                """);

            try {
                switch (Integer.parseInt(scanner.nextLine())) {
                    case 1 -> filter();
                    case 2 -> sort();
                    case 3 -> addTasks();
                    case 4 -> removeTasks();
                    case 5 -> addComment();
                    case 6 -> showComments();
                    case 7 -> checkComments();
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

    private void filterAndSort(SortType sortType) {
        log.info("Filter menu opened");
        System.out.println("""
            Enter the type of filter:
            1) Show all tasks
            2) Show only bugs
            3) Show only features
            """);
        try {
            switch (Integer.parseInt(scanner.nextLine())) {
                case 1 -> showTasks(sortType);
                case 2 -> showTasks(FilterType.BUG, sortType);
                case 3 -> showTasks(FilterType.FEATURE, sortType);
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
                case 1 -> filterAndSort(SortType.TITLE);
                case 2 -> filterAndSort(SortType.PRIORITY);
                case 3 -> filterAndSort(SortType.DEADLINE);
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
        var tasks = taskService.getTasks();
        if (tasks.isEmpty()) {
            log.warn("User have no tasks");
            System.out.println("You have no tasks.");
            return;
        }
        for (Task task : tasks) {
            System.out.println(task.toString());
        }
    }

    private void showTasks(SortType type) {
        log.info("Show sorted tasks by {}", type);
        var tasks = taskService.getSortedTasks(type);
        if (tasks.isEmpty()) {
            System.out.println("You have no tasks.");
            log.warn("User have no tasks of {}", type);
            return;
        }
        for (Task task : tasks) {
            System.out.println(task.toString());
        }
    }

    private void showTasks(FilterType type) {
        log.info("Show filtered tasks by {}", type);
        var tasks = taskService.getFilteredTasks(type);
        if (tasks.isEmpty()) {
            System.out.println("You have no tasks of this type.");
            log.warn("User have no tasks of this type.");
            return;
        }
        for (Task task : tasks) {
            System.out.println(task.toString());
        }
    }

    private void showTasks(FilterType type, SortType sortType) {
        log.info("Show filtered tasks by {}", type);
        log.info("Show sorted tasks by {}", sortType);
        var tasks = taskService.getSortedAndFilteredTasks(type, sortType);
        if (tasks.isEmpty()) {
            System.out.println("You have no tasks of this type.");
            log.warn("User have no tasks of this type.");
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

        TaskConsoleCreator taskConsoleCreator = new TaskConsoleCreator(scanner);
        try {
            switch (Integer.parseInt(scanner.nextLine())) {
                case 1 -> {
                    taskService.addTask(taskConsoleCreator.buildFeature());
                    log.info("Feature created");
                    System.out.println("Feature added successfully.");
                }
                case 2 -> {
                    taskService.addTask(taskConsoleCreator.buildBug());
                    log.info("Bug created");
                    System.out.println("Bug added successfully.");
                }
                default -> {
                    System.out.println("You picked wrong number. Try again.");
                    log.warn("User picked wrong number in add task menu");
                }
            }
        } catch (NumberFormatException numberFormatException) {
            log.error("User entered not a number", numberFormatException);
            System.out.println("You should enter a valid number. Try again.");
        } catch (PatternSyntaxException e) {
            log.error("User entered version in incorrect format", e);
            System.out.println("Incorrect format of version. Try again.");
            taskService.addTask(taskConsoleCreator.buildBug());
        } catch (InvalidParameterException invalidParameterException) {
            log.error("User entered version of app, that not allowed", invalidParameterException);
            System.out.println("This version is no longer supported.");
        } finally {
            scanner.nextLine();
        }
    }

    private void removeTasks() {
        log.info("Removing task menu opened");
        try {
            System.out.println("Choose task you want to remove:");
            taskService.removeTask(chooseTask());
            log.info("Task removed successfully.");
            System.out.println("Task removed successfully.");
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            System.out.println("You entered wrong number. Try again.");
            log.warn("User entered wrong number in remove task menu");
        } catch (NumberFormatException numberFormatException) {
            System.out.println("You should enter a valid number. Try again.");
            log.error("User entered not a number in remove menu", numberFormatException);
        } finally {
            scanner.nextLine();
        }
    }

    private void addComment() {
        CommentConsoleCreator creator = new CommentConsoleCreator(scanner);
        commentService.add(creator.create(chooseTask()));
    }

    private void showComments() {
        System.out.println("Enter nickname of the author");
        for (Comment comment : commentService.findAllByNickname(scanner.nextLine())) {
            System.out.println(comment.toString());
        };
    }

    public void checkComments() {
        System.out.println("Choose task you want to check comments: ");
        for (Comment comment : chooseTask().getComments()) {
            System.out.println(comment);
        };
    }

    private Task chooseTask() {
        log.info("Choose a task");
        List<Task> tasks = taskService.getTasks();
        if (tasks.isEmpty()) {
            System.out.println("You have no tasks to remove.");
            log.warn("User have no tasks to remove.");
            scanner.nextLine();
            throw new InvalidParameterException("You have no tasks");
        }
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i+1) + ") " + tasks.get(i).toString());
        }
        log.debug("Choosing a task");
        System.out.println("\nEnter number of task you want to remove: ");
        return tasks.get(Integer.parseInt(scanner.nextLine()) - 1);
    }

}
