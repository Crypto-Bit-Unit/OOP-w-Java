 
package ui;
import service.AuthService;

// ui/WelcomeFrame.java
import javax.swing.*;
import java.awt.*;

public class WelcomeFrame extends JFrame {
    public WelcomeFrame(AuthService auth) {
        super("Library Management System - Welcome");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        JPanel bg = UIUtils.gradientPanel(new Color(30, 60, 114), new Color(42, 82, 152));
        bg.setLayout(new BorderLayout());

        JLabel title = new JLabel("Welcome to Library Management System", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        JPanel btns = new JPanel();
        JButton login = new JButton("Login");
        JButton signup = new JButton("Sign up");
        login.setBackground(new Color(220, 230, 240));
        signup.setBackground(new Color(220, 230, 240));
        UIUtils.addHoverEffect(login);
        UIUtils.addHoverEffect(signup);
        btns.add(login);
        btns.add(signup);
        btns.setOpaque(false);

        bg.add(title, BorderLayout.NORTH);
        bg.add(btns, BorderLayout.CENTER);
        setContentPane(bg);
        UIUtils.centerOnScreen(this);

        login.addActionListener(e -> {
            new LoadingDialog(this, "Opening login...").showForMillis(600, () -> {
                new LoginFrame(auth, this).setVisible(true);
                setVisible(false);
            });
        });
        signup.addActionListener(e -> {
            new LoadingDialog(this, "Opening sign up...").showForMillis(600, () -> {
                new SignupFrame(auth, this).setVisible(true);
                setVisible(false);
            });
        });
    }
}

