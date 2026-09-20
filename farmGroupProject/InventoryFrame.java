import javax.swing.*;
import java.awt.*;

/**
 * PLACEHOLDER SCREEN for the Store Inventory section.
 *
 * The underlying InventoryManager / InventoryItem classes already
 * contain the full business logic (add, sell, restock, low-stock
 * checks) and are shared with the Store Sales screen. This GUI screen
 * itself is a filler to be built out in a future update -- it will
 * eventually expose Add Item / Restock / Low Stock Alert controls
 * the same way SalesFrame and PaymentsFrame already do.
 */
public class InventoryFrame extends JFrame {

    public InventoryFrame(InventoryManager inventoryManager) {
        setTitle("Store Inventory - Barnyard Supply & Services");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel message = new JLabel(
            "<html><center><h2>Store Inventory</h2>"
            + "This section is a placeholder.<br><br>"
            + "Coming soon: view inventory, add new items,<br>"
            + "restock items, and low-stock alerts.</center></html>",
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
