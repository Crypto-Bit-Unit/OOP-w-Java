
package ui;
import service.AuthService;
// ui/SignupFrame.java
import javax.swing.*;
import java.awt.*;

public class SignupFrame extends JFrame {
    private final AuthService auth;
    private final JFrame backTarget;

    public SignupFrame(AuthService auth, JFrame backTarget) {
        super("Sign up");
        this.auth = auth;
        this.backTarget = backTarget;
        setSize(460, 340);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UIUtils.centerOnScreen(this);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel lFull = new JLabel("Full name:");
        JTextField tfFull = new JTextField();
        JLabel lUser = new JLabel("Username:");
        JTextField tfUser = new JTextField();
        JLabel lPass = new JLabel("Password:");
        JPasswordField pfPass = new JPasswordField();

        JButton btnSignup = new JButton("Create account");
        JButton btnBack = new JButton("Back");
        UIUtils.addHoverEffect(btnSignup);
        UIUtils.addHoverEffect(btnBack);

        c.gridx = 0; c.gridy = 0; panel.add(lFull, c);
        c.gridx = 1; c.gridy = 0; panel.add(tfFull, c);
        c.gridx = 0; c.gridy = 1; panel.add(lUser, c);
        c.gridx = 1; c.gridy = 1; panel.add(tfUser, c);
        c.gridx = 0; c.gridy = 2; panel.add(lPass, c);
        c.gridx = 1; c.gridy = 2; panel.add(pfPass, c);
        c.gridx = 1; c.gridy = 3; panel.add(btnSignup, c);
        c.gridx = 0; c.gridy = 3; panel.add(btnBack, c);

        setContentPane(panel);

        btnBack.addActionListener(e -> {
            new LoadingDialog(this, "Returning...").showForMillis(400, () -> {
                backTarget.setVisible(true);
                dispose();
            });
        });

        btnSignup.addActionListener(e -> {
            String full = tfFull.getText().trim();
            String user = tfUser.getText().trim();
            String pass = new String(pfPass.getPassword());
            StringBuilder error = new StringBuilder();
            new LoadingDialog(this, "Creating account...").showForMillis(900, () -> {
                if (auth.register(user, pass, full, error)) {
                    JOptionPane.showMessageDialog(this, "Account created. You can log in now.");
                    new LoginFrame(auth, backTarget).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, error.toString(), "Sign up failed", JOptionPane.ERROR_MESSAGE);
                }
            });
        });
    }
}

