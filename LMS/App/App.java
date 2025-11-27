
//Starting point
package app;

import dao.UserDao;
import service.AuthService;
import ui.UIUtils;
import ui.WelcomeFrame;

import javax.swing.*;
import java.nio.file.Paths;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UIUtils.applyLookAndFeel();
            UIUtils.appPath = Paths.get("").toAbsolutePath().toString();

            UserDao userDao = new UserDao(Paths.get("data/users.txt"));
            AuthService auth = new AuthService(userDao);

            JFrame welcome = new WelcomeFrame(auth);
            welcome.setVisible(true);
        });
    }
}
