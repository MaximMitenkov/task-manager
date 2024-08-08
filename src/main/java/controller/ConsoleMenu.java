package controller;

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
                """);
            //TODO realization of showTasks with sorting and filter and task remover
            switch (scanner.nextLine().toLowerCase()) {
                case "1" -> showTasks();
                case "2" -> addTasks();
                case "3" -> removeTasks();
                case "exit" -> System.exit(0);
                default -> System.out.println("Invalid option");
            }
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

    private void removeTasks() {}

}


