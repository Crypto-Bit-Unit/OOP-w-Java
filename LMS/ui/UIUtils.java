
package ui;

// ui/UiUtils.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UIUtils {
    public static String appPath = "";
    public static void applyLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {}
    }

    public static JPanel gradientPanel(Color top, Color bottom) {
        return new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                int w = getWidth(), h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, top, 0, h, bottom);
                g2.setPaint(gp);
                g2.fillRect(0, 0, w, h);
                g2.dispose();
            }
        };
    }

    public static void addHoverEffect(AbstractButton btn) {
        Color base = btn.getBackground();
        btn.setContentAreaFilled(true);
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(base.brighter()); }
            public void mouseExited(MouseEvent e) { btn.setBackground(base); }
        });
    }

    public static void centerOnScreen(Window w) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - w.getWidth()) / 2;
        int y = (screen.height - w.getHeight()) / 2;
        w.setLocation(x, y);
    }

}

