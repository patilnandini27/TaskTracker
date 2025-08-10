package com.tasktracker;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class menu {
    private TaskManager manager = new TaskManager();
    private Scanner sc = new Scanner(System.in);
    private static final String FILE_NAME = "tasks.dat";

    public void start() {
        try {
            manager.getAllTasks().addAll(FileHandler.loadTasks(FILE_NAME));
        } catch (Exception e) {
            System.out.println("No previous data found.");
        }

        while (true) {
            System.out.println("\n--- Task Tracker ---");
            System.out.println("1. Add Task");
            System.out.println("2. View All Tasks");
            System.out.println("3. Mark Task Complete");
            System.out.println("4. Sort by Due Date");
            System.out.println("5. Sort by Priority");
            System.out.println("6. Search Task");
            System.out.println("7. Exit");
            System.out.print("Choose: ");

            switch (sc.nextInt()) {
                case 1 -> addTask();
                case 2 -> displayTasks(manager.getAllTasks());
                case 3 -> markComplete();
                case 4 -> displayTasks(manager.getTasksSortedByDate());
                case 5 -> displayTasks(manager.getTasksSortedByPriority());
                case 6 -> searchTask();
                case 7 -> exitApp();
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private void addTask() {
        sc.nextLine(); // flush
        System.out.print("Title: ");
        String title = sc.nextLine();
        System.out.print("Description: ");
        String desc = sc.nextLine();
        System.out.print("Due Date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(sc.nextLine());
        System.out.print("Priority (LOW, MEDIUM, HIGH): ");
        Priority priority = Priority.valueOf(sc.nextLine().toUpperCase());

        try {
            manager.addTask(title, desc, date, priority);
        } catch (InvalidTaskException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void displayTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }
        int index = 0;
        for (Task t : tasks) {
            System.out.println((index++) + ". " + t);
        }
    }

    private void markComplete() {
        System.out.print("Enter task index to mark complete: ");
        int index = sc.nextInt();
        try {
            manager.markTaskComplete(index);
        } catch (InvalidTaskException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void searchTask() {
        sc.nextLine();
        System.out.print("Keyword: ");
        String keyword = sc.nextLine();
        displayTasks(manager.searchTasks(keyword));
    }

    private void exitApp() {
        try {
            FileHandler.saveTasks(manager.getAllTasks(), FILE_NAME);
            System.out.println("Tasks saved. Goodbye!");
        } catch (Exception e) {
            System.out.println("Could not save tasks: " + e.getMessage());
        }
        System.exit(0);
    }
}
