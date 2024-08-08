package controller;

import dao.Storage;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class ConsoleMenu {



    final Scanner scanner = new Scanner(System.in);

    public void startMenu() {
        System.out.println("""
                WELCOME TO THE MENU
                1) Check my notes
                2) Create a new note
                3) Remove a note
                """);
        while (true) {
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
        System.out.println("");
    }

    private void addTasks() {}

    private void removeTasks() {}

}


