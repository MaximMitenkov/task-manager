package controller;

import lombok.NoArgsConstructor;

import java.util.Scanner;

@NoArgsConstructor
public class ConsoleMenu {

    private String userName;
    Scanner scanner = new Scanner(System.in);

    public ConsoleMenu(String userName) {
        this.userName = userName;
    }

    public void authorization() {
        System.out.println("Enter the user name: ");
        userName = scanner.nextLine();
        startMenu();
    }

    public void startMenu() {
        System.out.println("Welcome " + userName + "!");
        System.out.println("""
                WELCOME TO THE MENU
                1) Check my notes
                2) Create a new note
                3) Remove a note
                """);
        while (true) {
            switch (scanner.nextLine().toLowerCase()) {
                case "1" -> showNotes();
                case "2" -> addNote();
                case "3" -> removeNote();
                case "exit" -> System.exit(0);
                default -> System.out.println("Invalid option");
            }
        }
    }

    private void showNotes() {
        System.out.println("");
    }

    private void addNote() {}

    private void removeNote() {}

}


