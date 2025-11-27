package ui;

import service.AuthService;
import model.User;

import javax.swing.*;
import java.awt.*;
import ui.BorrowFrame;


public class MainDashboard extends JFrame {
    private final User currentUser;
    private final AuthService auth;

    public MainDashboard(User currentUser, AuthService auth) {
        super("Library Dashboard");
        this.currentUser = currentUser;
        this.auth = auth;

        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UIUtils.centerOnScreen(this);

        JPanel bg = UIUtils.gradientPanel(new Color(72, 85, 99), new Color(41, 50, 60));
        bg.setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("Your Dashboard — " + currentUser.getFullName(), SwingConstants.CENTER);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 20));
        header.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));

        // Top button bar
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        topBar.setOpaque(false);
        
        topBar.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY)); // bottom border line

        JButton books = new JButton("Book Reservation");
        JButton payment = new JButton("Payment");
        JButton borrow = new JButton("Borrow/Return");
        JButton logout = new JButton("Logout");

        Dimension btnSize = new Dimension(140, 30); // scaled down size
        for (JButton b : new JButton[]{books, payment, borrow, logout}) {
            b.setPreferredSize(btnSize);
            UIUtils.addHoverEffect(b);
            topBar.add(b);
        }
        

        // Center content (placeholder for future panels)
        JPanel content = new JPanel();
        content.setOpaque(false);
        
         borrow.addActionListener(e -> {
            new BorrowFrame(currentUser).setVisible(true);
        }); 
 
        /* ala tohh users.addActionListener(e -> {
            new ManageUsersFrame(auth).setVisible(true);
        });*/ 
        

        bg.add(header, BorderLayout.NORTH);
        bg.add(topBar, BorderLayout.CENTER);   // buttons at the top
        bg.add(content, BorderLayout.SOUTH);   // empty area below

        setContentPane(bg);

        
        books.addActionListener(e -> new BookFrame(currentUser).setVisible(true));
        borrow.addActionListener(e -> new BorrowFrame(currentUser).setVisible(true));
        
        // users.addActionListener(e -> new ManageUsersFrame(auth).setVisible(true));
        logout.addActionListener(e -> {
            new LoadingDialog(this, "Signing out...").showForMillis(600, () -> {
                JFrame welcome = new WelcomeFrame(auth);
                welcome.setVisible(true);
                dispose();
            });
        });
    }
}
