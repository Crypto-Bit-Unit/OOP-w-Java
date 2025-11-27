package ui.admin;

import service.AdminService;
import model.Book;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import ui.UIUtils;

public class ManageBooksFrame extends JFrame {
    private final AdminService adminService;

    public ManageBooksFrame(AdminService adminService) {
        this.adminService = adminService;

        setTitle("Manage Books");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        UIUtils.centerOnScreen(this); // Make sure UIUtils is imported or defined

        // Fetch books from service
        List<Book> books = adminService.getAllBooks();

        // Create table model and JTable
        JTable bookTable = new JTable(new BookTableModel(books));
        JScrollPane scrollPane = new JScrollPane(bookTable);

        // Layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
    }
}
