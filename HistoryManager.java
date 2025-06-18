package model;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class HistoryManager {
    private static final String DEFAULT_HISTORY_FILE = "calculator_history.log";
    private List<String> history;
    private String historyFilePath;

    public HistoryManager() {
        this.history = new ArrayList<>();
        this.historyFilePath = DEFAULT_HISTORY_FILE;
        loadHistory();
    }

    public void addToHistory(String expression, double result) {
        String entry = expression + " = " + result;
        history.add(entry);
        saveHistory();
    }

    public List<String> getHistory() {
        return new ArrayList<>(history);
    }

    public void saveToFile(String filePath) throws IOException {
        if (filePath == null || filePath.isEmpty()) {
            filePath = DEFAULT_HISTORY_FILE;
        }

        Path path = Paths.get(filePath);
        if (Files.isDirectory(path)) {
            path = path.resolve("log.log");
        } else if (!filePath.endsWith(".txt") && !filePath.endsWith(".log") && !filePath.endsWith(".md")) {
            path = Paths.get(filePath + ".log");
        }

        Files.write(path, history, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        System.out.println("History saved to: " + path.toAbsolutePath());
    }

    public void saveSelectedToFile(List<Integer> indices, String filePath) throws IOException {
        if (filePath == null || filePath.isEmpty()) {
            filePath = "selected_history.log";
        }

        Path path = Paths.get(filePath);
        if (Files.isDirectory(path)) {
            path = path.resolve("selected.log");
        }

        List<String> selectedEntries = new ArrayList<>();
        for (int i : indices) {
            if (i >= 0 && i < history.size()) {
                selectedEntries.add(history.get(i));
            }
        }

        Files.write(path, selectedEntries, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        System.out.println("Selected history saved to: " + path.toAbsolutePath());
    }

    private void loadHistory() {
        try {
            if (Files.exists(Paths.get(historyFilePath))) {
                history = Files.readAllLines(Paths.get(historyFilePath));
            }
        } catch (IOException e) {
            System.err.println("Error loading history: " + e.getMessage());
        }
    }

    private void saveHistory() {
        try {
            Files.write(Paths.get(historyFilePath), history, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error saving history: " + e.getMessage());
        }
    }
}
