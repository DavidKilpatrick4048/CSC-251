import javax.swing.*;
import java.awt.*;

/**
 * PLACEHOLDER SCREEN for the Animals Currently For Sale section.
 *
 * The underlying AnimalSalesManager / Animal classes already contain
 * the full business logic (add listing, sell, sold-out checks) and
 * are ready to be wired in. This GUI screen is a filler to be built
 * out in a future update -- it will eventually expose Add Animal /
 * Sell Animal controls the same way SalesFrame and PaymentsFrame
 * already do.
 */
public class AnimalSalesFrame extends JFrame {

    public AnimalSalesFrame(AnimalSalesManager animalSalesManager) {
        setTitle("Animals Currently For Sale - Barnyard Supply & Services");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel message = new JLabel(
            "<html><center><h2>Animals Currently For Sale</h2>"
            + "This section is a placeholder.<br><br>"
            + "Coming soon: view available animals (ducks, chickens,<br>"
            + "hamsters, rabbits, etc.), add new listings, and sell.</center></html>",
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
