package view;

public class MenuView {
    public void displayMenu() {
        System.out.println("\nCalculator Menu:");
        System.out.println("1. Calculate expression");
        System.out.println("2. View history");
        System.out.println("3. Save all history to file");
        System.out.println("4. Save selected history to file");
        System.out.println("5. Exit");
        System.out.print("Select option: ");
    }

    public void displayExitMessage() {
        System.out.println("Exiting calculator. Goodbye!");
    }
}
