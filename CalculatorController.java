package controller;

import model.CalculatorModel;
import model.HistoryManager;
import view.CalculatorView; 
import view.MenuView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CalculatorController {
    private CalculatorModel model;
    private CalculatorView view;
    private MenuView menuView;
    private HistoryManager historyManager;
    private boolean running;

    public CalculatorController(CalculatorModel model, CalculatorView view, MenuView menuView, HistoryManager historyManager) {
        this.model = model;
        this.view = view;
        this.menuView = menuView;
        this.historyManager = historyManager;
        this.running = true;
    }

    public void run() {
        while (running) {
            menuView.displayMenu();
            String input = view.getExpression(); // Reusing the same method for menu input

            try {
                int choice = Integer.parseInt(input);
                handleMenuChoice(choice);
            } catch (NumberFormatException e) {
                // Not a menu choice, try to calculate
                if (!input.equalsIgnoreCase("menu")) {
                    calculateExpression(input);
                }
            }
        }
    }

    private void handleMenuChoice(int choice) {
        switch (choice) {
            case 1:
                String expression = view.getExpression();
                if (!expression.equalsIgnoreCase("menu")) {
                    calculateExpression(expression);
                }
                break;
            case 2:
                view.displayHistory(historyManager.getHistory());
                break;
            case 3:
                saveAllHistory();
                break;
            case 4:
                saveSelectedHistory();
                break;
            case 5:
                running = false;
                menuView.displayExitMessage();
                break;
            default:
                view.displayError("Invalid menu choice");
        }
    }

    private void calculateExpression(String expression) {
        try {
            double result = model.calculateExpression(expression);
            view.displayResult(result);
            historyManager.addToHistory(expression, result);
        } catch (IllegalArgumentException e) {
            view.displayError(e.getMessage());
        }
    }

    private void saveAllHistory() {
        String filePath = view.getFilePath();
        try {
            historyManager.saveToFile(filePath);
            view.displayMessage("History saved successfully");
        } catch (IOException e) {
            view.displayError("Failed to save history: " + e.getMessage());
        }
    }

    private void saveSelectedHistory() {
        view.displayHistory(historyManager.getHistory());
        String indicesInput = view.getIndicesToSave();
        String filePath = view.getFilePath();

        try {
            List<Integer> indices = parseIndices(indicesInput);
            historyManager.saveSelectedToFile(indices, filePath);
            view.displayMessage("Selected history saved successfully");
        } catch (IOException e) {
            view.displayError("Failed to save selected history: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            view.displayError(e.getMessage());
        }
    }

    private List<Integer> parseIndices(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("No indices provided");
        }

        String[] parts = input.split(",");
        List<Integer> indices = new ArrayList<>();

        for (String part : parts) {
            try {
                // Convert to 0-based index
                int index = Integer.parseInt(part.trim()) - 1;
                indices.add(index);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid index: " + part);
            }
        }

        return indices;
    }
}
