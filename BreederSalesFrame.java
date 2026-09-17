import javax.swing.*;
import java.awt.*;

/**
 * PLACEHOLDER SCREEN for the Local Breeder Sales section.
 *
 * The underlying BreederSalesManager / BreederAnimal classes already
 * contain the full business logic (add consignment, sell with
 * farm/breeder commission split, sold-out checks) and are ready to be
 * wired in. This GUI screen is a filler to be built out in a future
 * update -- it will eventually expose Add Consignment / Sell Animal
 * controls the same way SalesFrame and PaymentsFrame already do.
 */
public class BreederSalesFrame extends JFrame {

    public BreederSalesFrame(BreederSalesManager breederSalesManager) {
        setTitle("Local Breeder Sales - Barnyard Supply & Services");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel message = new JLabel(
            "<html><center><h2>Local Breeder Sales</h2>"
            + "This section is a placeholder.<br><br>"
            + "Coming soon: manage specialty consignment animals<br>"
            + "from local breeders, including commission splits.</center></html>",
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
