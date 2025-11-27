package service;

import dao.BookDao;
import model.Book;
import model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookService {
    private final BookDao bookDao;

    public BookService(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    // --- Reservation ---
    public boolean reserveBook(User user, Book book, StringBuilder error) {
        if (book == null) { error.append("No book selected."); return false; }
        if (!book.isBorrowed()) { error.append("Book is available; no need to reserve."); return false; }
        if (book.isReserved()) { error.append("Book is already reserved by " + book.getReservedBy()); return false; }

        book.setReserved(true);
        book.setReservedBy(user.getUsername());
        bookDao.upsert(book);
        return true;
    }

    // --- Borrow ---
    public boolean borrowBook(User user, Book book, StringBuilder error) {
        if (book == null) { error.append("No book selected."); return false; }
        if (book.isBorrowed()) { error.append("Book is already borrowed."); return false; }
        if (book.isReserved() && !user.getUsername().equals(book.getReservedBy())) {
            error.append("Book is reserved by " + book.getReservedBy()); return false;
        }

        book.setBorrowed(true);
        book.setBorrowedBy(user.getUsername());
        book.setBorrowDate(LocalDate.now());
        book.setDueDate(LocalDate.now().plusDays(7)); // ✅ default 7-day due date

        // Clear reservation if borrower is the reserver
        if (book.isReserved() && user.getUsername().equals(book.getReservedBy())) {
            book.setReserved(false);
            book.setReservedBy("");
        }

        bookDao.upsert(book);
        return true;
    }

    // --- Return ---
    public boolean returnBook(User user, Book book, StringBuilder error) {
        if (book == null) { error.append("No book selected."); return false; }
        if (!book.isBorrowed()) { error.append("Book is not borrowed."); return false; }
        if (!user.getUsername().equals(book.getBorrowedBy())) {
            error.append("You cannot return a book borrowed by someone else."); return false;
        }

        book.setBorrowed(false);
        book.setBorrowedBy("");
        book.setBorrowDate(null);
        book.setDueDate(null);

        bookDao.upsert(book);
        return true;
    }

    // --- Cancel Reservation ---
    public boolean cancelReservation(User user, Book book, StringBuilder error) {
        if (!book.isReserved()) { error.append("Book is not reserved."); return false; }
        if (!user.getUsername().equals(book.getReservedBy())) {
            error.append("You cannot cancel someone else's reservation."); return false;
        }
        book.setReserved(false);
        book.setReservedBy("");
        bookDao.upsert(book);
        return true;
    }

    // --- Utilities ---
    public List<Book> listAll() { return bookDao.getAll(); }

    public Optional<Book> findById(String id) { return bookDao.findById(id); }

    public List<Book> searchByTitle(String q) {
        if (q == null || q.isBlank()) return bookDao.getAll();
        return bookDao.findByTitle(q);
    }

    public List<Book> sortByTitle(List<Book> books) {
        return books.stream()
                .sorted((a, b) -> a.getTitle().compareToIgnoreCase(b.getTitle()))
                .collect(Collectors.toList());
    }

    public List<Book> sortByGenre(List<Book> books) {
        return books.stream()
                .sorted((a, b) -> a.getGenre().compareToIgnoreCase(b.getGenre()))
                .collect(Collectors.toList());
    }
}
