import controller.ConsoleMenu;
import dao.Storage;
import service.Manager;

public class Main {
    public static void main(String[] args) {
        Storage storage = new Storage();
        Manager manager = new Manager(storage);
        ConsoleMenu consoleMenu = new ConsoleMenu(manager);
        consoleMenu.startMenu();
    }
}
