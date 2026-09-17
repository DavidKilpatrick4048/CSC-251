import javax.swing.*;
import java.awt.*;

/**
 * GUI screen for the Store Sales section.
 * Lets staff ring up a sale (which reduces inventory) and review
 * sales history / total revenue.
 */
public class SalesFrame extends JFrame {

    private InventoryManager inventoryManager;
    private SalesManager salesManager;
    private JTextArea displayArea;

    public SalesFrame(InventoryManager inventoryManager, SalesManager salesManager) {
        this.inventoryManager = inventoryManager;
        this.salesManager = salesManager;

        setTitle("Store Sales - Barnyard Supply & Services");
        setSize(650, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        refreshDisplay();

        JButton ringUpButton = new JButton("Ring Up Sale");
        JButton viewInventoryButton = new JButton("View Available Items");
        JButton refreshButton = new JButton("Refresh History");

        ringUpButton.addActionListener(e -> ringUpSale());
        viewInventoryButton.addActionListener(e -> viewInventory());
        refreshButton.addActionListener(e -> refreshDisplay());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(ringUpButton);
        buttonPanel.add(viewInventoryButton);
        buttonPanel.add(refreshButton);

        setLayout(new BorderLayout());
        add(new JLabel("  Store Sales History", SwingConstants.LEFT), BorderLayout.NORTH);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void refreshDisplay() {
        displayArea.setText(salesManager.getFormattedHistory());
    }

    private void viewInventory() {
        JOptionPane.showMessageDialog(this, inventoryManager.getFormattedList(),
                "Available Items", JOptionPane.INFORMATION_MESSAGE);
    }

    private void ringUpSale() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog(this,
                    "Enter Item ID to sell (see 'View Available Items'):"));
            int qty = Integer.parseInt(JOptionPane.showInputDialog(this, "Quantity sold:"));

            StoreSale sale = salesManager.sellSingleItem(id, qty);
            // Decision structure: report success or the specific reason for failure
            if (sale != null) {
                refreshDisplay();
                JOptionPane.showMessageDialog(this, "Sale complete!\n" + sale.getReceipt());
            } else {
                InventoryItem item = inventoryManager.findById(id);
                if (item == null) {
                    JOptionPane.showMessageDialog(this, "No item found with that ID.",
                            "Not Found", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Not enough stock. Only " + item.getQuantityOnHand() + " available.",
                            "Insufficient Stock", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers.",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }
}
