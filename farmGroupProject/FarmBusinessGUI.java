import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GridLayout;

/**
 * Main menu window for Barnyard Supply & Services.
 * Holds the shared manager objects (created once, here) so that data
 * entered in one screen -- like a store sale reducing inventory, or a
 * scheduled appointment showing up on the payments screen -- is
 * visible everywhere else in the program.
 */
public class FarmBusinessGUI extends JFrame {

    // Shared business-logic managers, created once and passed into each screen
    private InventoryManager inventoryManager = new InventoryManager();
    private SalesManager salesManager = new SalesManager(inventoryManager);
    private ServiceManager serviceManager = new ServiceManager();
    private AnimalSalesManager animalSalesManager = new AnimalSalesManager();
    private BreederSalesManager breederSalesManager = new BreederSalesManager();

    public FarmBusinessGUI() {
        // Set the window title
        setTitle("Barnyard Supply & Services");
        // Set the window size
        setSize(600, 400);
        // Close the program when the window is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Open the window in the center of the screen
        setLocationRelativeTo(null);

        // Create GUI components
        JLabel titleLabel = new JLabel("Barnyard Supply & Services", SwingConstants.CENTER);
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));

        JButton inventoryButton = new JButton("Store Inventory");
        JButton salesButton = new JButton("Store Sales");
        JButton schedulingButton = new JButton("Service Scheduling");
        JButton paymentsButton = new JButton("Service Payments");
        JButton animalSalesButton = new JButton("Animals Currently For Sale");
        JButton breederSalesButton = new JButton("Local Breeder Sales");

        // Decision-free wiring: each button simply opens its own screen,
        // passing along whichever shared manager(s) it needs
        inventoryButton.addActionListener(e -> new InventoryFrame(inventoryManager).setVisible(true));
        salesButton.addActionListener(e -> new SalesFrame(inventoryManager, salesManager).setVisible(true));
        schedulingButton.addActionListener(e -> new SchedulingFrame(serviceManager).setVisible(true));
        paymentsButton.addActionListener(e -> new PaymentsFrame(serviceManager).setVisible(true));
        animalSalesButton.addActionListener(e -> new AnimalSalesFrame(animalSalesManager).setVisible(true));
        breederSalesButton.addActionListener(e -> new BreederSalesFrame(breederSalesManager).setVisible(true));

        // Create a panel to hold the components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1, 5, 8));
        panel.add(titleLabel);
        panel.add(inventoryButton);
        panel.add(salesButton);
        panel.add(schedulingButton);
        panel.add(paymentsButton);
        panel.add(animalSalesButton);
        panel.add(breederSalesButton);

        // Add the panel to the JFrame
        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FarmBusinessGUI window = new FarmBusinessGUI();
            window.setVisible(true);
        });
    }
}
