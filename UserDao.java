package dao;

import model.User;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class UserDao {
    private final Path storePath;

    public UserDao(Path storePath) {
        this.storePath = storePath;
        try {
            if (!Files.exists(storePath)) {
                Files.createDirectories(storePath.getParent());
                Files.createFile(storePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to init user store", e);
        }
    }

    public synchronized Optional<User> findByUsername(String username) {
        return readAll().stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst();
    }

    public synchronized void save(User user) {
        List<User> all = readAll().stream()
                .filter(u -> !u.getUsername().equalsIgnoreCase(user.getUsername()))
                .collect(Collectors.toList());
        all.add(user);
        writeAll(all);
    }

    public synchronized List<User> readAll() {
        try {
            List<String> lines = Files.readAllLines(storePath);
            List<User> users = new ArrayList<>();
            for (String l : lines) {
                if (l.isBlank()) continue;
                String[] parts = l.split(",", -1);
                if (parts.length >= 3) {
                    users.add(new User(parts[0], parts[1], parts[2]));
                }
            }
            return users;
        } catch (IOException e) {
            throw new RuntimeException("Read users failed", e);
        }
    }

    private synchronized void writeAll(List<User> users) {
        try (BufferedWriter bw = Files.newBufferedWriter(storePath)) {
            for (User u : users) {
                bw.write(u.getUsername() + "," + u.getHashedPassword() + "," + u.getFullName());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Write users failed", e);
        }
    }

    public List<User> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void update(User user) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void delete(String username) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
