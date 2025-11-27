package ui.admin;

import service.AdminService;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import ui.UIUtils;

public class ManageUsersFrame extends JFrame {
    private final AdminService adminService;

    public ManageUsersFrame(AdminService adminService) {
        this.adminService = adminService;

        setTitle("Manage Users");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        UIUtils.centerOnScreen(this);

        List<User> users = adminService.getAllUsers();
        JTable userTable = new JTable(new UserTableModel(users));
        JScrollPane scrollPane = new JScrollPane(userTable);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
    }
}
