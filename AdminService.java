package service;

import dao.BookDao;
import dao.UserDao;
import model.Book;
import model.User;

import java.util.List;

public class AdminService {
    private final BookDao bookDao;
    private final UserDao userDao;

    public AdminService(BookDao bookDao, UserDao userDao) {
        this.bookDao = bookDao;
        this.userDao = userDao;
    }

    // 📚 Book Management
    public List<Book> getAllBooks() {
        return bookDao.findAll();
    }

    public void addBook(Book book) {
        bookDao.save(book);
    }

    public void updateBook(Book book) {
        bookDao.update(book);
    }

    public void deleteBook(int bookId) {
        bookDao.delete(bookId);
    }

    // 👥 User Management
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public void addUser(User user) {
        userDao.save(user);
    }

    public void updateUser(User user) {
        userDao.update(user);
    }

    public void deleteUser(String username) {
        userDao.delete(username);
    }
}
