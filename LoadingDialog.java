
package ui;

// ui/LoadingDialog.java
import javax.swing.*;
import java.awt.*;

public class LoadingDialog extends JDialog {
    private final JProgressBar progress;

    public LoadingDialog(Window owner, String message) {
        super(owner, "Loading", ModalityType.APPLICATION_MODAL);
        progress = new JProgressBar();
        progress.setIndeterminate(true);

        JLabel label = new JLabel(message, SwingConstants.CENTER);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        panel.add(label, BorderLayout.NORTH);
        panel.add(progress, BorderLayout.CENTER);

        setContentPane(panel);
        setSize(300, 120);
        UIUtils.centerOnScreen(this);
    }

    public void showForMillis(int millis, Runnable after) {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            protected Void doInBackground() throws Exception {
                Thread.sleep(millis);
                return null;
            }
            protected void done() {
                dispose();
                if (after != null) SwingUtilities.invokeLater(after);
            }
        };
        worker.execute();
        setVisible(true);
    }
}

