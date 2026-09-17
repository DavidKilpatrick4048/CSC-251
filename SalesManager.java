import java.util.ArrayList;
import java.util.List;

/**
 * Processes store sales against the shared InventoryManager (so a
 * sale actually decrements stock) and keeps a running sales history.
 */
public class SalesManager {

    private InventoryManager inventoryManager;
    private List<StoreSale> salesHistory;

    public SalesManager(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
        this.salesHistory = new ArrayList<>();
    }

    public List<StoreSale> getSalesHistory() {
        return salesHistory;
    }

    /**
     * Attempts to sell a quantity of one inventory item and, if
     * successful, records it as a brand-new StoreSale.
     * Decision structure: checks stock through InventoryManager first.
     */
    public StoreSale sellSingleItem(int itemId, int quantity) {
        InventoryItem item = inventoryManager.findById(itemId);
        if (item == null) {
            return null; // item not found
        }
        if (quantity <= 0 || quantity > item.getQuantityOnHand()) {
            return null; // not enough stock, or bad quantity
        }

        item.sell(quantity);
        StoreSale sale = new StoreSale();
        sale.addLineItem(new SaleLineItem(item.getName(), quantity, item.getUnitPrice()));
        salesHistory.add(sale);
        return sale;
    }

    public double getTotalRevenue() {
        double total = 0.0;
        for (StoreSale sale : salesHistory) {
            total += sale.getTotal();
        }
        return total;
    }

    public String getFormattedHistory() {
        if (salesHistory.isEmpty()) {
            return "No sales recorded yet.";
        }
        StringBuilder sb = new StringBuilder();
        for (StoreSale sale : salesHistory) {
            sb.append(sale.getReceipt()).append("\n");
        }
        sb.append(String.format("=== TOTAL REVENUE: $%.2f ===", getTotalRevenue()));
        return sb.toString();
    }
}
