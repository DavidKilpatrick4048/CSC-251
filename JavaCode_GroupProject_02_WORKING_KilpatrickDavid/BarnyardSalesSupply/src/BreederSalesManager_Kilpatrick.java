/** Reuses Johnson's breeder manager; adds stable IDs and boundary validation. */
public class BreederSalesManager_Kilpatrick extends BreederManager_Johnson {
    public BreederAnimal_Kilpatrick addAnimal(String breeder, String contact, String species,
            String breed, long cost, long price, String acquired) {
        int id = getTotalCount() == 0 ? 1 : ((BreederAnimal_Kilpatrick)getAllAnimals().get(getTotalCount()-1)).getId() + 1;
        BreederAnimal_Kilpatrick animal = new BreederAnimal_Kilpatrick(id, breeder, contact, species, breed, cost, price, acquired);
        addBreederAnimal(animal);
        return animal;
    }
    @Override public void addBreederAnimal(Breeder_Johnson animal) {
        if (!(animal instanceof BreederAnimal_Kilpatrick)) throw new IllegalArgumentException("An animal ID is required.");
        BreederAnimal_Kilpatrick b = (BreederAnimal_Kilpatrick)animal;
        if (getTotalCount() > 0 && b.getId() <= ((BreederAnimal_Kilpatrick)getAllAnimals().get(getTotalCount()-1)).getId())
            throw new IllegalArgumentException("Duplicate or unordered breeder ID.");
        super.addBreederAnimal(animal);
    }
    public BreederAnimal_Kilpatrick findById(int id) {
        for (Breeder_Johnson animal : getAllAnimals()) {
            BreederAnimal_Kilpatrick b = (BreederAnimal_Kilpatrick)animal;
            if (b.getId() == id) return b;
        }
        return null;
    }
    public void sellById(int id) {
        BreederAnimal_Kilpatrick animal = findById(id);
        if (animal == null) throw new IllegalArgumentException("Select a breeder animal.");
        if (animal.isSold()) throw new IllegalArgumentException("This animal has already been sold.");
        super.sellBreederAnimal(getAllAnimals().indexOf(animal));
    }
    @Override public boolean removeBreederAnimal(int index) {
        // Keep IDs and the complete audit history stable in the combined application.
        return false;
    }
    public long getProfitCents() {
        long total = 0;
        for (Breeder_Johnson animal : getAllAnimals()) {
            BreederAnimal_Kilpatrick b = (BreederAnimal_Kilpatrick)animal;
            if (b.isSold()) total += b.getPriceCents() - b.getCostCents();
        }
        return total;
    }
}
