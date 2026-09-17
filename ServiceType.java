/**
 * Represents a type of service the farm offers (basic vet care,
 * nail trims, vaccinations, grooming, etc.) and its standard price.
 */
public class ServiceType {

    private String name;
    private String description;
    private double basePrice;

    public ServiceType(String name, String description, double basePrice) {
        this.name = name;
        this.description = description;
        this.basePrice = basePrice;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getBasePrice() { return basePrice; }

    @Override
    public String toString() {
        return String.format("%-18s $%-7.2f - %s", name, basePrice, description);
    }
}
