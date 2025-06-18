package view;

import java.util.List;
import java.util.Scanner;

public class CalculatorView {
    private Scanner scanner;

    public CalculatorView() {
        scanner = new Scanner(System.in);
    }

    public String getExpression() {
        System.out.print("Enter expression (or 'menu' to show menu): ");
        return scanner.nextLine().trim();
    }

    public void displayResult(double result) {
        System.out.println("Result: " + result);
    }

    public void displayError(String message) {
        System.out.println("Error: " + message);
    }

    public void displayHistory(List<String> history) {
        if (history.isEmpty()) {
            System.out.println("History is empty");
            return;
        }

        System.out.println("\nCalculation History:");
        for (int i = 0; i < history.size(); i++) {
            System.out.println((i + 1) + ". " + history.get(i));
        }
        System.out.println();
    }

    public String getFilePath() {
        System.out.print("Enter file path (leave empty for default): ");
        return scanner.nextLine().trim();
    }

    public String getIndicesToSave() {
        System.out.print("Enter indices to save (comma separated, e.g. 1,3,5): ");
        return scanner.nextLine().trim();
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
}
