package dao;

import model.Book;

import java.io.*;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class BookDao {
    private final Path filePath;

    // CSV layout:
    // id,title,author,genre,borrowed,borrowedBy,borrowDate,dueDate,reserved,reservedBy,coverImage
    public BookDao(Path filePath) {
        this.filePath = filePath;
        ensureFileExists();
    }

    private void ensureFileExists() {
        try {
            File file = filePath.toFile();
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create books file", e);
        }
    }

    // --- Load all books ---
    public List<Book> getAll() {
        List<Book> books = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] p = line.split(",", -1);

                String id = get(p, 0);
                String title = get(p, 1);
                String author = get(p, 2);
                String genre = get(p, 3);
                boolean borrowed = Boolean.parseBoolean(get(p, 4));
                String borrowedBy = get(p, 5);
                LocalDate borrowDate = parseDate(get(p, 6));
                LocalDate dueDate = parseDate(get(p, 7));
                boolean reserved = Boolean.parseBoolean(get(p, 8));
                String reservedBy = get(p, 9);
                String coverImage = get(p, 10);

                books.add(new Book(id, title, author, genre,
                        borrowed, borrowedBy, borrowDate,
                        dueDate, reserved, reservedBy, coverImage));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }

    // --- Find by ID ---
    public Optional<Book> findById(String id) {
        return getAll().stream().filter(b -> b.getId().equals(id)).findFirst();
    }

    // --- Find by Title ---
    public List<Book> findByTitle(String title) {
        String t = title.trim().toLowerCase();
        return getAll().stream()
                .filter(b -> b.getTitle().toLowerCase().contains(t))
                .collect(Collectors.toList());
    }

    // --- Insert or Update ---
    public void upsert(Book book) {
        List<Book> all = getAll();
        boolean updated = false;
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getId().equals(book.getId())) {
                all.set(i, book);
                updated = true;
                break;
            }
        }
        if (!updated) all.add(book);
        writeAll(all);
    }

    // --- Write all books back to file ---
    public void writeAll(List<Book> books) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath.toFile()))) {
            for (Book b : books) {
                bw.write(String.join(",",
                        safe(b.getId()),
                        safe(b.getTitle()),
                        safe(b.getAuthor()),
                        safe(b.getGenre()),
                        String.valueOf(b.isBorrowed()),
                        safe(b.getBorrowedBy()),
                        b.getBorrowDate() == null ? "" : b.getBorrowDate().toString(),
                        b.getDueDate() == null ? "" : b.getDueDate().toString(),
                        String.valueOf(b.isReserved()),
                        safe(b.getReservedBy()),
                        safe(b.getCoverImage())
                ));
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to write books file", e);
        }
    }

    // --- Helpers ---
    private static String get(String[] arr, int idx) {
        return arr.length > idx ? arr[idx] : "";
    }

    private static String safe(String s) {
        return s == null ? "" : s.replace(",", " ");
    }

    private static LocalDate parseDate(String s) {
        if (s == null || s.isBlank()) return null;
        try { return LocalDate.parse(s); } catch (Exception e) { return null; }
    }

    public List<Book> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void save(Book book) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void update(Book book) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void delete(int bookId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
    