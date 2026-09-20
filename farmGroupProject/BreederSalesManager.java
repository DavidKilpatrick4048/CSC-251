import java.util.ArrayList;
import java.util.List;

/**
 * Manages specialty animals brought in on consignment from local
 * breeders for occasional resale, including the farm/breeder
 * commission split when one is sold.
 */
public class BreederSalesManager {

    private List<BreederAnimal> breederAnimals;

    public BreederSalesManager() {
        breederAnimals = new ArrayList<>();
        seedSampleData();
    }

    private void seedSampleData() {
        breederAnimals.add(new BreederAnimal("Rabbit", "Netherland Dwarf", 12, 60.00, 3,
                "Sunny Acres Rabbitry", "555-0142", 20.0));
        breederAnimals.add(new BreederAnimal("Chicken", "Silkie", 10, 40.00, 2,
                "Miller Family Farm", "555-0198", 15.0));
    }

    public List<BreederAnimal> getAllBreederAnimals() {
        return breederAnimals;
    }

    public BreederAnimal addConsignment(String species, String breed, int ageInWeeks, double price,
                                         int qty, String breederName, String breederContact,
                                         double commissionPercent) {
        BreederAnimal animal = new BreederAnimal(species, breed, ageInWeeks, price, qty,
                breederName, breederContact, commissionPercent);
        breederAnimals.add(animal);
        return animal;
    }

    public BreederAnimal findById(int id) {
        for (BreederAnimal a : breederAnimals) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    /**
     * Decision structure: only sell if the animal exists and is in
     * stock, then compute and report the farm/breeder split.
     */
    public String sellBreederAnimal(int id) {
        BreederAnimal animal = findById(id);
        if (animal == null) {
            return "No breeder animal found with that ID.";
        }
        if (!animal.isAvailable()) {
            return animal.getSpecies() + " (consignment) is currently sold out.";
        }
        double[] split = animal.calculateSaleSplit();
        animal.sellOne();
        return String.format(
            "Sold one %s %s for $%.2f%nFarm keeps: $%.2f  |  Owed to %s: $%.2f",
            animal.getBreed(), animal.getSpecies(), animal.getPrice(),
            split[0], animal.getBreederName(), split[1]
        );
    }

    public List<BreederAnimal> getAvailableBreederAnimals() {
        List<BreederAnimal> available = new ArrayList<>();
        for (BreederAnimal a : breederAnimals) {
            if (a.isAvailable()) {
                available.add(a);
            }
        }
        return available;
    }

    public String getFormattedList() {
        if (breederAnimals.isEmpty()) {
            return "No local breeder consignments yet.";
        }
        StringBuilder sb = new StringBuilder();
        for (BreederAnimal a : breederAnimals) {
            sb.append(a.getDisplayInfo()).append("\n");
        }
        return sb.toString();
    }
}
