package com.tasktracker;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class TaskManager {
    private List<Task> tasks = new ArrayList<>();

    public void addTask(String title, String description, LocalDate dueDate, Priority priority) throws InvalidTaskException {
        if (title == null || title.isBlank()) throw new InvalidTaskException("Task title cannot be empty");
        tasks.add(new Task(title, description, dueDate, priority));
    }

    public void markTaskComplete(int index) throws InvalidTaskException {
        if (index < 0 || index >= tasks.size()) throw new InvalidTaskException("Invalid task index");
        tasks.get(index).markComplete();
    }

    public List<Task> getTasksSortedByDate() {
        return tasks.stream()
                .sorted(Comparator.comparing(Task::getDueDate))
                .collect(Collectors.toList());
    }

    public List<Task> getTasksSortedByPriority() {
        return tasks.stream()
                .sorted(Comparator.comparing(Task::getPriority))
                .collect(Collectors.toList());
    }

    public List<Task> searchTasks(String keyword) {
        return tasks.stream()
                .filter(t -> t.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Task> getAllTasks() {
        return tasks;
    }
}
