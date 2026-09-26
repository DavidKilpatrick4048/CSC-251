/** Kilpatrick's farm-animal stock model; one record represents a similar batch. */
public class Animal_Kilpatrick {
    private final int id;
    private String species;
    private String breed;
    private int ageInWeeks;
    private long priceCents;
    private int quantityAvailable;
    public Animal_Kilpatrick(int id, String species, String breed, int age, long price, int qty) {
        if (id < 1) throw new IllegalArgumentException("Invalid animal ID.");
        this.id = id;
        update(species, breed, age, price, qty);
    }
    public void update(String species, String breed, int age, long price, int qty) {
        String s = Validation_Kilpatrick.required(species, "Species");
        String b = Validation_Kilpatrick.required(breed, "Breed");
        Validation_Kilpatrick.nonnegative(age, "Age");
        Validation_Kilpatrick.nonnegative(qty, "Quantity");
        Validation_Kilpatrick.cents(Validation_Kilpatrick.dollars(price));
        this.species = s; this.breed = b; this.ageInWeeks = age;
        this.priceCents = price; this.quantityAvailable = qty;
    }
    public int getId() { return id; }
    public String getSpecies() { return species; }
    public String getBreed() { return breed; }
    public int getAgeInWeeks() { return ageInWeeks; }
    public long getPriceCents() { return priceCents; }
    public double getPrice() { return priceCents / 100.0; }
    public int getQuantityAvailable() { return quantityAvailable; }
    public boolean isAvailable() { return quantityAvailable > 0; }
    public boolean sell(int qty) {
        if (qty <= 0 || qty > quantityAvailable) return false;
        quantityAvailable -= qty;
        return true;
    }
    public boolean sellOne() { return sell(1); }
    public void addStock(int qty) {
        if (qty <= 0) throw new IllegalArgumentException("Restock quantity must be positive.");
        quantityAvailable = Validation_Kilpatrick.nonnegative(Math.addExact(quantityAvailable, qty), "Stock");
    }
    @Override public String toString() { return "#" + id + " " + breed + " " + species; }
}
