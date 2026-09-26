/** Integration adapter: inherits Johnson's animal data and sold/profit logic unchanged. */
public class BreederAnimal_Kilpatrick extends Breeder_Johnson {
    private final int id;
    public BreederAnimal_Kilpatrick(int id, String breeder, String contact, String species,
            String breed, long cost, long price, String acquired) {
        super(Validation_Kilpatrick.required(breeder, "Breeder name"),
              Validation_Kilpatrick.required(contact, "Breeder contact"),
              Validation_Kilpatrick.required(species, "Animal type"),
              Validation_Kilpatrick.required(breed, "Breed"),
              checked(cost), checked(price), Validation_Kilpatrick.date(acquired).format(Validation_Kilpatrick.DATE));
        if (id < 1) throw new IllegalArgumentException("Invalid breeder animal ID.");
        this.id = id;
    }
    private static double checked(long amount) {
        Validation_Kilpatrick.cents(Validation_Kilpatrick.dollars(amount));
        return amount / 100.0;
    }
    public int getId() { return id; }
    public long getCostCents() { return Validation_Kilpatrick.cents(getCost()); }
    public long getPriceCents() { return Validation_Kilpatrick.cents(getPrice()); }
    @Override public void setPrice(double price) {
        if (isSold()) throw new IllegalArgumentException("A sold animal's historical price cannot change.");
        Validation_Kilpatrick.cents(price);
        super.setPrice(price);
    }
}
