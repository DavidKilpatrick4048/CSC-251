import java.util.ArrayList;
import java.util.List;

/**
 * Owns the master list of InventoryItem objects and contains all of the
 * business logic for adding, selling, restocking, and reporting on
 * store inventory. Shared by the Store Inventory screen and the
 * Store Sales screen (so a sale actually reduces stock).
 */
public class InventoryManager {

    private List<InventoryItem> items;

    public InventoryManager() {
        items = new ArrayList<>();
        seedSampleData();
    }

    /** A little starter data so the GUI isn't empty when it first opens. */
    private void seedSampleData() {
        items.add(new InventoryItem("Chicken Feed 25lb", "Feed", 18.99, 40, 10));
        items.add(new InventoryItem("Rabbit Pellets 10lb", "Feed", 12.49, 25, 8));
        items.add(new InventoryItem("Small Animal Bedding", "Bedding", 9.99, 30, 10));
        items.add(new InventoryItem("Poultry Waterer", "Supplies", 14.50, 12, 5));
        items.add(new InventoryItem("Leash - Medium", "Supplies", 11.25, 15, 5));
    }

    public List<InventoryItem> getAllItems() {
        return items;
    }

    public InventoryItem addItem(String name, String category, double price, int qty, int reorderLevel) {
        InventoryItem item = new InventoryItem(name, category, price, qty, reorderLevel);
        items.add(item);
        return item;
    }

    public InventoryItem findById(int id) {
        for (InventoryItem item : items) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public boolean sellItem(int id, int quantity) {
        InventoryItem item = findById(id);
        if (item == null) {
            return false;
        }
        return item.sell(quantity);
    }

    public boolean restockItem(int id, int quantity) {
        InventoryItem item = findById(id);
        if (item == null) {
            return false;
        }
        item.restock(quantity);
        return true;
    }

    /** Decision structure applied across the whole list to build alerts. */
    public List<InventoryItem> getLowStockItems() {
        List<InventoryItem> lowStock = new ArrayList<>();
        for (InventoryItem item : items) {
            if (item.isLowStock()) {
                lowStock.add(item);
            }
        }
        return lowStock;
    }

    public String getFormattedList() {
        if (items.isEmpty()) {
            return "No inventory items yet.";
        }
        StringBuilder sb = new StringBuilder();
        for (InventoryItem item : items) {
            sb.append(item.getDisplayInfo()).append("\n");
        }
        return sb.toString();
    }
}
