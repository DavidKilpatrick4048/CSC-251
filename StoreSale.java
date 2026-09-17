import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents one completed store transaction, made up of one or more
 * SaleLineItem entries (a "shopping cart" that has been checked out).
 */
public class StoreSale {

    private static int nextId = 1;
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");

    private int id;
    private LocalDateTime saleDateTime;
    private List<SaleLineItem> lineItems;

    public StoreSale() {
        this.id = nextId++;
        this.saleDateTime = LocalDateTime.now();
        this.lineItems = new ArrayList<>();
    }

    public int getId() { return id; }
    public LocalDateTime getSaleDateTime() { return saleDateTime; }
    public List<SaleLineItem> getLineItems() { return lineItems; }

    public void addLineItem(SaleLineItem line) {
        lineItems.add(line);
    }

    public double getTotal() {
        double total = 0.0;
        for (SaleLineItem line : lineItems) {
            total += line.getLineTotal();
        }
        return total;
    }

    /** Builds a printable receipt for the GUI to display. */
    public String getReceipt() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sale #").append(id).append("  ").append(saleDateTime.format(FORMAT)).append("\n");
        for (SaleLineItem line : lineItems) {
            sb.append("   ").append(line).append("\n");
        }
        sb.append(String.format("   TOTAL: $%.2f\n", getTotal()));
        return sb.toString();
    }

    @Override
    public String toString() {
        return getReceipt();
    }
}
