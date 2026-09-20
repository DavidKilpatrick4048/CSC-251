/**
 * Represents a farm-raised animal available for sale in the store
 * (ducks, chickens, hamsters, rabbits, etc.).
 *
 * This is the base class for animal inventory. BreederAnimal extends
 * this class to add consignment-specific information for animals
 * brought in from local breeders.
 */
public class Animal {

    // Shared counter so every animal gets a unique ID automatically
    protected static int nextId = 1;

    protected int id;
    protected String species;      // e.g. "Duck", "Chicken", "Hamster", "Rabbit"
    protected String breed;
    protected int ageInWeeks;
    protected double price;
    protected int quantityAvailable;

    public Animal(String species, String breed, int ageInWeeks, double price, int quantityAvailable) {
        this.id = nextId++;
        this.species = species;
        this.breed = breed;
        this.ageInWeeks = ageInWeeks;
        this.price = price;
        this.quantityAvailable = quantityAvailable;
    }

    // ----- Getters -----
    public int getId() { return id; }
    public String getSpecies() { return species; }
    public String getBreed() { return breed; }
    public int getAgeInWeeks() { return ageInWeeks; }
    public double getPrice() { return price; }
    public int getQuantityAvailable() { return quantityAvailable; }

    /**
     * Decision structure: an animal is only available if there is at
     * least one left in stock.
     */
    public boolean isAvailable() {
        return quantityAvailable > 0;
    }

    /**
     * Reduces the quantity available by one when an animal is sold.
     * Returns false (and changes nothing) if none are left, so the
     * caller can decide how to react.
     */
    public boolean sellOne() {
        if (quantityAvailable > 0) {
            quantityAvailable--;
            return true;
        }
        return false;
    }

    public void addStock(int amount) {
        if (amount > 0) {
            quantityAvailable += amount;
        }
    }

    /**
     * Produces a human-readable summary line. Subclasses override this
     * to add their own extra details (this is polymorphism supporting
     * code reuse between Animal and BreederAnimal).
     */
    public String getDisplayInfo() {
        return String.format(
            "#%-3d %-10s %-12s Age(wks): %-4d Price: $%-8.2f Available: %d",
            id, species, breed, ageInWeeks, price, quantityAvailable
        );
    }

    @Override
    public String toString() {
        return getDisplayInfo();
    }
}
