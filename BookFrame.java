package ui;

import dao.BookDao;
import model.Book;
import model.User;
import service.BookService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.nio.file.Paths;
import java.util.List;

public class BookFrame extends JFrame {
    private final User currentUser;
    private final BookService bookService;
    private JTable table;
    private DefaultTableModel model;

    public BookFrame(User currentUser) {
        super("Borrow / Return Books");
        this.currentUser = currentUser;
        this.bookService = new BookService(new BookDao(Paths.get("data/books.txt")));

        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        UIUtils.centerOnScreen(this);

        // --- Table setup ---
        model = new DefaultTableModel(new String[]{
                "ID", "Title", "Author", "Genre", "Borrowed By", "Due Date"
        }, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        table.setRowHeight(22);

        // --- Buttons ---
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnBorrow = new JButton("Borrow");
        JButton btnReturn = new JButton("Return");
        UIUtils.addHoverEffect(btnBorrow);
        UIUtils.addHoverEffect(btnReturn);
        bottom.add(btnBorrow);
        bottom.add(btnReturn);

        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        // --- Load books initially ---
        loadBooks();

        // --- Borrow button action ---
        btnBorrow.addActionListener(e -> {
            Book b = getSelectedBook();
            StringBuilder err = new StringBuilder();
            if (bookService.borrowBook(currentUser, b, err)) {
                JOptionPane.showMessageDialog(this,
                        "Book borrowed successfully!\nDue date: " + b.getDueDate(),
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                loadBooks();
            } else {
                JOptionPane.showMessageDialog(this, err.toString(),
                        "Borrow failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        // --- Return button action ---
        btnReturn.addActionListener(e -> {
            Book b = getSelectedBook();
            StringBuilder err = new StringBuilder();
            if (bookService.returnBook(currentUser, b, err)) {
                JOptionPane.showMessageDialog(this,
                        "Book returned successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                loadBooks();
            } else {
                JOptionPane.showMessageDialog(this, err.toString(),
                        "Return failed", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    // --- Load books into table ---
    private void loadBooks() {
        model.setRowCount(0);
        List<Book> books = bookService.listAll();
        for (Book b : books) {
            model.addRow(new Object[]{
                    b.getId(), b.getTitle(), b.getAuthor(), b.getGenre(),
                    b.isBorrowed() ? b.getBorrowedBy() : "",
                    b.getDueDate() != null ? b.getDueDate().toString() : ""
            });
        }
    }

    // --- Get selected book ---
    private Book getSelectedBook() {
        int row = table.getSelectedRow();
        if (row < 0) return null;
        String id = String.valueOf(model.getValueAt(row, 0));
        return bookService.findById(id).orElse(null);
    }
}
