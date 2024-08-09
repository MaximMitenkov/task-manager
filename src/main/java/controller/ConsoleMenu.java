package controller;

import dao.FilterType;
import dao.SortType;
import entities.Task;
import service.Manager;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleMenu {

    final private Manager manager;

    public ConsoleMenu(Manager manager) {
        this.manager = manager;
    }

    final Scanner scanner = new Scanner(System.in);

    public void startMenu() {
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
                switch (Integer.getInteger(scanner.nextLine())) {
                    case 1 -> filter();
                    case 2 -> sort();
                    case 3 -> addTasks();
                    case 4 -> removeTasks();
                    case 0 -> System.exit(0);
                    default -> System.out.println("You picked wrong number. Try again.");
                }
            } catch (SecurityException securityException) {
                System.out.println("You should enter a valid number. Try again.");
            }
        }
    }

    private void filter() {
        System.out.println("""
            Enter the type of filter:
            1) Show all tasks
            2) Show only bugs
            3) Show only features
            """);
        try {
            switch (Integer.getInteger(scanner.nextLine())) {
                case 1 -> showTasks();
                case 2 -> showTasks(FilterType.BUG);
                case 3 -> showTasks(FilterType.FEATURE);
                default -> System.out.println("Invalid option");
            }
        } catch (SecurityException securityException) {
            System.out.println("You should enter a valid number. Try again.");
        }
    }

    private void sort() {
        System.out.println("""
                Enter the type of sort method:
                1) Title sort
                2) Priority sort
                3) Deadline sort
                """);
        try {
            switch (Integer.getInteger(scanner.nextLine())) {
                case 1 -> showTasks(SortType.TITLE);
                case 2 -> showTasks(SortType.PRIORITY);
                case 3 -> showTasks(SortType.DEADLINE);
                default -> System.out.println("You entered wrong number. Try again.");
            }
        } catch (SecurityException securityException) {
            System.out.println("You should enter a valid number. Try again.");
        }
    }

    private void showTasks() {
        for (Task task : manager.getTasks()) {
            task.printConsole();
        }
    }

    private void showTasks(SortType type) {
        for (Task task : manager.getSortedTasks(type)) {
            task.printConsole();
        }
    }

    private void showTasks(FilterType type) {
        for (Task task : manager.getFilteredTasks(type)) {
            task.printConsole();
        }
    }

    private void addTasks() {
        System.out.println("""
            Enter the type of the task you would like to add:
            1) Add a new Feature;
            2) Add a new Bug;
         """);

        TaskCreator taskCreator = new TaskCreator(scanner);
        try {
            switch (Integer.getInteger(scanner.nextLine())) {
                case 1 -> manager.addTask(taskCreator.buildFeature());
                case 2 -> manager.addTask(taskCreator.buildBug());
                default -> System.out.println("You picked wrong number. Try again.");
            }
        } catch (SecurityException securityException) {
            System.out.println("You should enter a valid number. Try again.");
        }
    }

    private void removeTasks() {
        try {
            ArrayList<Task> tasks = manager.getTasks();
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i+1) + ") " + tasks.get(i).toString());
            }
            System.out.println("\nEnter number of task you want to remove: ");
            manager.removeTask(tasks.get(Integer.getInteger(scanner.nextLine()) - 1));
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            System.out.println("You entered wrong number. Try again.");
        } catch (SecurityException securityException) {
            System.out.println("You should enter a valid number. Try again.");
        }
    }

}


