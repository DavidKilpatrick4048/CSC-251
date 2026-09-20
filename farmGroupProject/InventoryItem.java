/**
 * Represents a single product sold in the store (feed, leashes,
 * bedding, basic supplies, etc. -- the "PetSmart / Tractor Supply"
 * style merchandise).
 */
public class InventoryItem {

    private static int nextId = 1;

    private int id;
    private String name;
    private String category;
    private double unitPrice;
    private int quantityOnHand;
    private int reorderLevel; // when stock is at/below this, flag as low

    public InventoryItem(String name, String category, double unitPrice,
                          int quantityOnHand, int reorderLevel) {
        this.id = nextId++;
        this.name = name;
        this.category = category;
        this.unitPrice = unitPrice;
        this.quantityOnHand = quantityOnHand;
        this.reorderLevel = reorderLevel;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getUnitPrice() { return unitPrice; }
    public int getQuantityOnHand() { return quantityOnHand; }
    public int getReorderLevel() { return reorderLevel; }

    /**
     * Attempts to sell a quantity of this item.
     * Decision structure: only allow the sale if there is enough stock.
     */
    public boolean sell(int quantity) {
        if (quantity <= 0) {
            return false;
        }
        if (quantity > quantityOnHand) {
            return false; // not enough stock
        }
        quantityOnHand -= quantity;
        return true;
    }

    public void restock(int quantity) {
        if (quantity > 0) {
            quantityOnHand += quantity;
        }
    }

    /** Decision structure used by the manager to build low-stock alerts. */
    public boolean isLowStock() {
        return quantityOnHand <= reorderLevel;
    }

    public String getDisplayInfo() {
        String flag = isLowStock() ? "  <-- LOW STOCK" : "";
        return String.format(
            "#%-3d %-20s %-12s $%-8.2f Qty: %-5d Reorder@ %-4d%s",
            id, name, category, unitPrice, quantityOnHand, reorderLevel, flag
        );
    }

    @Override
    public String toString() {
        return getDisplayInfo();
    }
}
