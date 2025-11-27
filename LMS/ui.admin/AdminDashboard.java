package ui.admin;

import model.User;
import service.AdminService;
import service.AuthService;
import ui.LoginFrame;
import ui.UIUtils;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {
    private final AdminService adminService;
    private final User admin;

    public AdminDashboard(AdminService adminService, User admin) {
        this.adminService = adminService;
        this.admin = admin;

        setTitle("Admin Dashboard");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UIUtils.centerOnScreen(this);

        JLabel welcomeLabel = new JLabel("Welcome, " + admin.getFullName());
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton manageBooksBtn = new JButton("Manage Books");
        JButton manageUsersBtn = new JButton("Manage Users");
        JButton logoutBtn = new JButton("Logout");

        UIUtils.addHoverEffect(manageBooksBtn);
        UIUtils.addHoverEffect(manageUsersBtn);
        UIUtils.addHoverEffect(logoutBtn);

        manageBooksBtn.addActionListener(e -> new ManageBooksFrame(adminService).setVisible(true));
        manageUsersBtn.addActionListener(e -> new ManageUsersFrame(adminService).setVisible(true));
        logoutBtn.addActionListener(e -> {
            new LoginFrame(new AuthService(), this).setVisible(true);
            dispose();
        });

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonPanel.add(manageBooksBtn);
        buttonPanel.add(manageUsersBtn);
        buttonPanel.add(logoutBtn);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }
}
