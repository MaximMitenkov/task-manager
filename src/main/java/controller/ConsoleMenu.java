package controller;

import entities.Bug;
import entities.Feature;
import entities.Task;
import lombok.extern.slf4j.Slf4j;
import service.Manager;

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
                1) Check my notes
                2) Create a new note
                3) Remove a note
                4) Sort options
                """);
            //TODO realization of showTasks with sorting and filter and task remover
            switch (scanner.nextLine().toLowerCase()) {
                case "1" -> filter();
                case "2" -> addTasks();
                case "3" -> removeTasks();
                case "4" -> changeSortFlags();
                case "exit" -> System.exit(0);
                default -> System.out.println("Invalid option");
            }
        }
    }

    private void changeSortFlags() {
        System.out.println("Enter number of sort you want to change: ");
        boolean[] currentFlags = manager.getSortFlags();
        System.out.println("1) Sort by Title " + currentFlags[0]);
        System.out.println("2) Sort by Priority " + currentFlags[1]);
        System.out.println("3) Sort by Deadline " + currentFlags[2]);
        switch (scanner.nextLine()) {
            case "1" -> manager.changeSortFlag(0);
            case "2" -> manager.changeSortFlag(1);
            case "3" -> manager.changeSortFlag(2);
            default -> System.out.println("Invalid option");
        }
    }

    private void filter() {
        System.out.println("""
Enter the type of filter:
    1) Show all tasks
    2) Show only bugs
    3) Show only features
""");
        switch (scanner.nextLine()) {
            case "1" -> showTasks();
            case "2" -> manager.getFilteredTasks(Bug.class);
            case "3" -> manager.getFilteredTasks(Feature.class);
            default -> System.out.println("Invalid option");
        }
    }

    private void showTasks() {
        Task[] tasks = manager.getSortedTasks();
        for (Task task : tasks) {
            System.out.println(task.toString());
        }
    }

    private void addTasks() {
        System.out.println("""
    Enter the type of the task you would like to add:
    1) Add a new Feature;
    2) Add a new Bug;
    
    """);

        TaskCreator taskCreator = new TaskCreator(scanner);
        switch (scanner.nextLine()) {
            case "1" -> manager.addTask(taskCreator.buildFeature());
            case "2" -> manager.addTask(taskCreator.buildBug());
            default -> System.out.println("Something went wrong");
        }
    }

    private void removeTasks() {
        Task[] tasks = manager.getFilteredTasks(Task.class);
        for (int i = 0; i < tasks.length; i++) {
            System.out.println((i+1) + ") " + tasks[i].toString());
        }
        System.out.println("Enter number of sort you want to remove: ");
        manager.removeTask(tasks[scanner.nextInt() + 1]);
    }

}


