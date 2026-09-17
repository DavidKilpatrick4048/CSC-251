import javax.swing.*;
import java.awt.*;

/**
 * GUI screen for the Service Payments section.
 * Shows outstanding balances and lets staff record payments against
 * scheduled appointments (partial or paid-in-full).
 */
public class PaymentsFrame extends JFrame {

    private ServiceManager serviceManager;
    private JTextArea displayArea;

    public PaymentsFrame(ServiceManager serviceManager) {
        this.serviceManager = serviceManager;

        setTitle("Service Payments - Barnyard Supply & Services");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        refreshDisplay();

        JButton payButton = new JButton("Record Payment");
        JButton refreshButton = new JButton("Refresh Outstanding Balances");

        payButton.addActionListener(e -> recordPayment());
        refreshButton.addActionListener(e -> refreshDisplay());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(payButton);
        buttonPanel.add(refreshButton);

        setLayout(new BorderLayout());
        add(new JLabel("  Outstanding / Unpaid Appointments", SwingConstants.LEFT), BorderLayout.NORTH);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void refreshDisplay() {
        displayArea.setText(serviceManager.getFormattedUnpaidList());
    }

    private void recordPayment() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog(this,
                    "Appointment ID (see list for outstanding balances):"));
            double amount = Double.parseDouble(JOptionPane.showInputDialog(this, "Payment amount:"));

            String result = serviceManager.recordPayment(id, amount);
            refreshDisplay();
            JOptionPane.showMessageDialog(this, result);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers.",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }
}
