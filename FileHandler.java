package com.tasktracker;

import java.io.*;
import java.util.List;

public class FileHandler {

    public static void saveTasks(List<Task> tasks, String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(tasks);
        }
    }

    public static List<Task> loadTasks(String filename) throws IOException, ClassNotFoundException {
        File file = new File(filename);
        if (!file.exists()) return new java.util.ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<Task>) ois.readObject();
        }
    }
}
