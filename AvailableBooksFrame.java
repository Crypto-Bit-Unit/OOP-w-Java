package ui;

import dao.BookDao;
import model.Book;
import service.BookService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.nio.file.Paths;
import java.util.List;

public class AvailableBooksFrame extends JFrame {
    private final BookService bookService;
    private JTable table;
    private JLabel lblCover;
    private JTextArea txtDetails;

    public AvailableBooksFrame() {
        super("Available Books");
        this.bookService = new BookService(new BookDao(Paths.get("data/books.txt")));

        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        UIUtils.centerOnScreen(this);

        // --- Table model ---
        DefaultTableModel model = new DefaultTableModel(new String[]{
                "ID", "Title", "Author", "Genre", "Borrowed", "Reserved"
        }, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        table.setRowHeight(22);

        // --- Right panel for details + cover ---
        JPanel rightPanel = new JPanel(new BorderLayout());
        lblCover = new JLabel("", SwingConstants.CENTER);
        lblCover.setPreferredSize(new Dimension(200, 250));
        txtDetails = new JTextArea();
        txtDetails.setEditable(false);
        txtDetails.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtDetails.setLineWrap(true);
        txtDetails.setWrapStyleWord(true);

        rightPanel.add(lblCover, BorderLayout.NORTH);
        rightPanel.add(new JScrollPane(txtDetails), BorderLayout.CENTER);

        // --- Split pane ---
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(table), rightPanel);
        split.setDividerLocation(550);

        setContentPane(split);

        // --- Load books ---
        loadBooks(model);

        // --- Selection listener ---
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                showBookDetails(model);
            }
        });
    }

    private void loadBooks(DefaultTableModel model) {
        model.setRowCount(0);
        List<Book> books = bookService.listAll();
        for (Book b : books) {
            model.addRow(new Object[]{
                    b.getId(), b.getTitle(), b.getAuthor(), b.getGenre(),
                    b.isBorrowed() ? "Yes" : "No",
                    b.isReserved() ? "Yes" : "No"
            });
        }
    }

    private void showBookDetails(DefaultTableModel model) {
        int row = table.getSelectedRow();
        if (row < 0) return;

        String id = (String) model.getValueAt(row, 0);
        Book b = bookService.findById(id).orElse(null);
        if (b == null) return;

        // --- Cover image ---
        if (b.getCoverImage() != null && !b.getCoverImage().isBlank()) {
            ImageIcon icon = new ImageIcon(b.getCoverImage());
            Image scaled = icon.getImage().getScaledInstance(200, 250, Image.SCALE_SMOOTH);
            lblCover.setIcon(new ImageIcon(scaled));
            lblCover.setText("");
        } else {
            lblCover.setIcon(null);
            lblCover.setText("No Image");
        }

        // --- Details ---
        txtDetails.setText(
                "Title: " + b.getTitle() + "\n" +
                "Author: " + b.getAuthor() + "\n" +
                "Genre: " + b.getGenre() + "\n" +
                "Borrowed: " + (b.isBorrowed() ? "Yes (" + b.getBorrowedBy() + ")" : "No") + "\n" +
                "Reserved: " + (b.isReserved() ? "Yes (" + b.getReservedBy() + ")" : "No") + "\n" +
                "Due Date: " + (b.getDueDate() != null ? b.getDueDate() : "N/A")
        );
    }
}
