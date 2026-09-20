import javax.swing.*;
import java.awt.*;

/**
 * PLACEHOLDER SCREEN for the Service Scheduling section.
 *
 * The underlying ServiceManager / ServiceType / Appointment classes
 * already contain the full business logic (service menu, scheduling
 * with a time-slot conflict check, complete/cancel status) and are
 * shared with the Service Payments screen. This GUI screen is a
 * filler to be built out in a future update -- it will eventually
 * let staff pick a service from a list during entry and schedule an
 * appointment, the same way SalesFrame and PaymentsFrame already work.
 */
public class SchedulingFrame extends JFrame {

    public SchedulingFrame(ServiceManager serviceManager) {
        setTitle("Service Scheduling - Barnyard Supply & Services");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel message = new JLabel(
            "<html><center><h2>Service Scheduling</h2>"
            + "This section is a placeholder.<br><br>"
            + "Coming soon: view the service menu, schedule<br>"
            + "appointments (with a service selection list),<br>"
            + "and mark appointments completed or cancelled.</center></html>",
            SwingConstants.CENTER
        );

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);

        setLayout(new BorderLayout());
        add(message, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
