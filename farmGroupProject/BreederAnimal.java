/**
 * Represents a specialty animal brought in on consignment from a local
 * breeder for resale. Extends Animal to reuse all of the base
 * inventory/availability logic, and adds breeder-specific fields plus
 * the commission split calculation.
 */
public class BreederAnimal extends Animal {

    private String breederName;
    private String breederContact;
    private double farmCommissionPercent; // % of the sale price the farm keeps

    public BreederAnimal(String species, String breed, int ageInWeeks, double price,
                          int quantityAvailable, String breederName, String breederContact,
                          double farmCommissionPercent) {
        super(species, breed, ageInWeeks, price, quantityAvailable);
        this.breederName = breederName;
        this.breederContact = breederContact;
        this.farmCommissionPercent = farmCommissionPercent;
    }

    public String getBreederName() { return breederName; }
    public String getBreederContact() { return breederContact; }
    public double getFarmCommissionPercent() { return farmCommissionPercent; }

    /**
     * Decision/calculation logic: splits the sale price between the
     * farm's commission and the amount owed back to the breeder.
     * Returns a 2-element array: [farmCut, breederCut].
     */
    public double[] calculateSaleSplit() {
        double farmCut;
        if (farmCommissionPercent < 0) {
            farmCut = 0; // guard against bad data
        } else if (farmCommissionPercent > 100) {
            farmCut = price; // guard against bad data
        } else {
            farmCut = price * (farmCommissionPercent / 100.0);
        }
        double breederCut = price - farmCut;
        return new double[] { farmCut, breederCut };
    }

    @Override
    public String getDisplayInfo() {
        return super.getDisplayInfo() + String.format(
            "  | Breeder: %-15s Contact: %-12s Farm Commission: %.0f%%",
            breederName, breederContact, farmCommissionPercent
        );
    }
}
