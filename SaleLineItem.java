/**
 * A single line within a StoreSale receipt: one inventory item,
 * the quantity sold, and the price at the time of the sale.
 */
public class SaleLineItem {

    private String itemName;
    private int quantitySold;
    private double unitPriceAtSale;

    public SaleLineItem(String itemName, int quantitySold, double unitPriceAtSale) {
        this.itemName = itemName;
        this.quantitySold = quantitySold;
        this.unitPriceAtSale = unitPriceAtSale;
    }

    public String getItemName() { return itemName; }
    public int getQuantitySold() { return quantitySold; }
    public double getUnitPriceAtSale() { return unitPriceAtSale; }

    public double getLineTotal() {
        return quantitySold * unitPriceAtSale;
    }

    @Override
    public String toString() {
        return String.format("%-20s x%-3d @ $%-7.2f = $%.2f",
                itemName, quantitySold, unitPriceAtSale, getLineTotal());
    }
}
