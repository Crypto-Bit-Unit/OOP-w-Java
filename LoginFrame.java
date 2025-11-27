package ui.admin;

import model.User;
import service.AuthService;
import service.AdminService;
import dao.BookDao;
import dao.UserDao;
import ui.admin.AdminDashboard;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Paths;
import ui.LoadingDialog;
import ui.MainDashboard;

public class LoginFrame extends JFrame {
    private final AuthService auth;
    private final JFrame backTarget;

    public LoginFrame(AuthService auth, JFrame backTarget) {
        super("Login");
        this.auth = auth;
        this.backTarget = backTarget;

        setSize(420, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UIUtils.centerOnScreen(this);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel lUser = new JLabel("Username:");
        JTextField tfUser = new JTextField();
        JLabel lPass = new JLabel("Password:");
        JPasswordField pfPass = new JPasswordField();

        JButton btnLogin = new JButton("Login");
        JButton btnBack = new JButton("Back");
        UIUtils.addHoverEffect(btnLogin);
        UIUtils.addHoverEffect(btnBack);

        c.gridx = 0; c.gridy = 0; panel.add(lUser, c);
        c.gridx = 1; c.gridy = 0; panel.add(tfUser, c);
        c.gridx = 0; c.gridy = 1; panel.add(lPass, c);
        c.gridx = 1; c.gridy = 1; panel.add(pfPass, c);
        c.gridx = 1; c.gridy = 2; panel.add(btnLogin, c);
        c.gridx = 0; c.gridy = 2; panel.add(btnBack, c);

        setContentPane(panel);

        // 🔙 Back button logic
        btnBack.addActionListener(e -> {
            new LoadingDialog(this, "Returning...").showForMillis(400, () -> {
                backTarget.setVisible(true);
                dispose();
            });
        });

        // 🔐 Login button logic
        btnLogin.addActionListener(e -> {
            String username = tfUser.getText().trim();
            String password = new String(pfPass.getPassword());

            new LoadingDialog(this, "Authenticating...").showForMillis(800, () -> {
                auth.login(username, password).ifPresentOrElse(user -> {
                    JOptionPane.showMessageDialog(this, "Welcome, " + user.getFullName() + "!");

                    if ("admin".equals(user.getRole())) {
                        AdminService adminService = new AdminService(
                            new BookDao(Paths.get("data/books.txt")),
                            new UserDao(Paths.get("data/users.txt"))
                        );
                        new AdminDashboard(adminService, user).setVisible(true);
                    } else {
                        new MainDashboard(user, auth).setVisible(true);
                    }

                    dispose();
                }, () -> JOptionPane.showMessageDialog(
                        this,
                        "Invalid username or password.",
                        "Login failed",
                        JOptionPane.ERROR_MESSAGE
                ));
            });
        });
    }
}
