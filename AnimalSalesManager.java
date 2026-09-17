import java.util.ArrayList;
import java.util.List;

/**
 * Manages the farm's own animals currently for sale (ducks, chickens,
 * hamsters, rabbits, etc. raised on the farm itself).
 */
public class AnimalSalesManager {

    private List<Animal> animals;

    public AnimalSalesManager() {
        animals = new ArrayList<>();
        seedSampleData();
    }

    private void seedSampleData() {
        animals.add(new Animal("Chicken", "Rhode Island Red", 8, 15.00, 10));
        animals.add(new Animal("Duck", "Pekin", 6, 18.00, 6));
        animals.add(new Animal("Rabbit", "Holland Lop", 10, 35.00, 4));
        animals.add(new Animal("Hamster", "Syrian", 4, 12.00, 8));
    }

    public List<Animal> getAllAnimals() {
        return animals;
    }

    public Animal addAnimal(String species, String breed, int ageInWeeks, double price, int qty) {
        Animal animal = new Animal(species, breed, ageInWeeks, price, qty);
        animals.add(animal);
        return animal;
    }

    public Animal findById(int id) {
        for (Animal a : animals) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    /** Decision structure: only sell if the animal exists and is in stock. */
    public String sellAnimal(int id) {
        Animal animal = findById(id);
        if (animal == null) {
            return "No animal found with that ID.";
        }
        if (!animal.isAvailable()) {
            return animal.getSpecies() + " is currently sold out.";
        }
        animal.sellOne();
        return "Sold one " + animal.getBreed() + " " + animal.getSpecies() + " for $"
                + String.format("%.2f", animal.getPrice());
    }

    public List<Animal> getAvailableAnimals() {
        List<Animal> available = new ArrayList<>();
        for (Animal a : animals) {
            if (a.isAvailable()) {
                available.add(a);
            }
        }
        return available;
    }

    public String getFormattedList() {
        if (animals.isEmpty()) {
            return "No animals listed yet.";
        }
        StringBuilder sb = new StringBuilder();
        for (Animal a : animals) {
            sb.append(a.getDisplayInfo()).append("\n");
        }
        return sb.toString();
    }
}
