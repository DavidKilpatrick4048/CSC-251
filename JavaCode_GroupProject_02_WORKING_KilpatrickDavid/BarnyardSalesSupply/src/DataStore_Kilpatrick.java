import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/** New integration: plain-text Properties persistence (no external database/libraries). */
public final class DataStore_Kilpatrick {
    private DataStore_Kilpatrick() { }
    private static void put(Properties p, String key, Object value) { p.setProperty(key, String.valueOf(value)); }
    private static String get(Properties p, String key) {
        String value = p.getProperty(key);
        if (value == null) throw new IllegalArgumentException("Missing saved field: " + key);
        return value;
    }
    private static int integer(Properties p, String key) { return Integer.parseInt(get(p, key)); }
    private static long number(Properties p, String key) { return Long.parseLong(get(p, key)); }
    private static int count(Properties p, String key) {
        int value = integer(p, key);
        if (value < 0 || value > 100000) throw new IllegalArgumentException("Invalid saved record count.");
        return value;
    }
    public static Properties encode(FarmData_Kilpatrick data) {
        Properties p = new Properties();
        put(p, "version", "1");
        put(p, "products", data.inventory.getAllItems().size());
        int n = 0;
        for (InventoryItem_Kilpatrick a : data.inventory.getAllItems()) {
            String k = "product." + n++ + ".";
            put(p,k+"id",a.getId()); put(p,k+"name",a.getName()); put(p,k+"category",a.getCategory());
            put(p,k+"price",a.getPriceCents()); put(p,k+"qty",a.getQuantityOnHand()); put(p,k+"reorder",a.getReorderLevel());
        }
        put(p, "animals", data.animals.getAllAnimals().size()); n = 0;
        for (Animal_Kilpatrick a : data.animals.getAllAnimals()) {
            String k = "animal." + n++ + ".";
            put(p,k+"id",a.getId()); put(p,k+"species",a.getSpecies()); put(p,k+"breed",a.getBreed());
            put(p,k+"age",a.getAgeInWeeks()); put(p,k+"price",a.getPriceCents()); put(p,k+"qty",a.getQuantityAvailable());
        }
        put(p, "breeders", data.breeders.getTotalCount()); n = 0;
        for (Breeder_Johnson original : data.breeders.getAllAnimals()) {
            BreederAnimal_Kilpatrick a = (BreederAnimal_Kilpatrick)original;
            String k = "breeder." + n++ + ".";
            put(p,k+"id",a.getId()); put(p,k+"name",a.getBreederName()); put(p,k+"contact",a.getContactInfo());
            put(p,k+"species",a.getAnimalType()); put(p,k+"breed",a.getBreed()); put(p,k+"cost",a.getCostCents());
            put(p,k+"price",a.getPriceCents()); put(p,k+"date",a.getDateAcquired()); put(p,k+"sold",a.isSold());
        }
        put(p, "appointments", data.services.getAllAppointments().size()); n = 0;
        for (ServiceAppointment_Johnson original : data.services.getAllAppointments()) {
            Appointment_Kilpatrick a = (Appointment_Kilpatrick)original;
            String k = "appointment." + n++ + ".";
            put(p,k+"id",a.getId()); put(p,k+"customer",a.getCustomerName()); put(p,k+"phone",a.getPhoneNumber());
            put(p,k+"animal",a.getAnimalType()); put(p,k+"service",a.getServiceType()); put(p,k+"start",a.getStart());
            put(p,k+"duration",a.getDurationMinutes()); put(p,k+"cost",a.getCostCents());
            put(p,k+"paid",a.getPaidCents()); put(p,k+"status",a.getStatus());
        }
        put(p, "sales", data.sales.getSalesHistory().size()); n = 0;
        for (StoreSale_Kilpatrick s : data.sales.getSalesHistory()) {
            String k = "sale." + n++ + ".";
            put(p,k+"id",s.getId()); put(p,k+"type",s.getType()); put(p,k+"customer",s.getCustomer());
            put(p,k+"method",s.getMethod()); put(p,k+"time",s.getSaleDateTime()); put(p,k+"lines",s.getLineItems().size());
            int j = 0;
            for (SaleLineItem_Kilpatrick line : s.getLineItems()) {
                String t = k + "line." + j++ + ".";
                put(p,t+"id",line.getItemId()); put(p,t+"name",line.getItemName());
                put(p,t+"qty",line.getQuantitySold()); put(p,t+"price",line.getUnitPriceCents());
            }
        }
        put(p, "payments", data.payments.size()); n = 0;
        for (Payment_Kilpatrick a : data.payments) {
            String k = "payment." + n++ + ".";
            put(p,k+"id",a.id); put(p,k+"type",a.type); put(p,k+"ref",a.referenceId); put(p,k+"customer",a.customer);
            put(p,k+"amount",a.amountCents); put(p,k+"method",a.method); put(p,k+"time",a.dateTime);
        }
        return p;
    }
    public static FarmData_Kilpatrick decode(Properties p) {
        if (!get(p,"version").equals("1")) throw new IllegalArgumentException("Unsupported saved data version.");
        FarmData_Kilpatrick data = new FarmData_Kilpatrick();
        for (int i=0; i<count(p,"products"); i++) {
            String k = "product." + i + ".";
            data.inventory.restore(new InventoryItem_Kilpatrick(integer(p,k+"id"),get(p,k+"name"),get(p,k+"category"),
                    number(p,k+"price"),integer(p,k+"qty"),integer(p,k+"reorder")));
        }
        for (int i=0; i<count(p,"animals"); i++) {
            String k = "animal." + i + ".";
            data.animals.restore(new Animal_Kilpatrick(integer(p,k+"id"),get(p,k+"species"),get(p,k+"breed"),
                    integer(p,k+"age"),number(p,k+"price"),integer(p,k+"qty")));
        }
        for (int i=0; i<count(p,"breeders"); i++) {
            String k = "breeder." + i + ".";
            BreederAnimal_Kilpatrick a = new BreederAnimal_Kilpatrick(integer(p,k+"id"),get(p,k+"name"),get(p,k+"contact"),
                    get(p,k+"species"),get(p,k+"breed"),number(p,k+"cost"),number(p,k+"price"),get(p,k+"date"));
            String sold = get(p,k+"sold");
            if (!sold.equals("true") && !sold.equals("false")) throw new IllegalArgumentException("Invalid sold flag.");
            if (sold.equals("true")) a.markAsSold();
            data.breeders.addBreederAnimal(a);
        }
        for (int i=0; i<count(p,"appointments"); i++) {
            String k = "appointment." + i + ".";
            Appointment_Kilpatrick a = new Appointment_Kilpatrick(integer(p,k+"id"),get(p,k+"customer"),get(p,k+"phone"),
                    get(p,k+"animal"),get(p,k+"service"),LocalDateTime.parse(get(p,k+"start")),integer(p,k+"duration"),number(p,k+"cost"));
            long paid = number(p,k+"paid");
            if (paid < 0) throw new IllegalArgumentException("Invalid paid balance.");
            if (paid > 0) a.receive(paid);
            String status = get(p,k+"status");
            if (status.equals("Completed")) a.markCompleted();
            else if (status.equals("Cancelled")) {
                if (!a.markCancelled()) throw new IllegalArgumentException("Cancelled appointment still has a payment balance.");
            } else if (!status.equals("Scheduled")) throw new IllegalArgumentException("Invalid appointment status.");
            data.services.scheduleAppointment(a);
        }
        for (int i=0; i<count(p,"sales"); i++) {
            String k = "sale." + i + ".";
            List<SaleLineItem_Kilpatrick> lines = new ArrayList<>();
            for (int j=0; j<count(p,k+"lines"); j++) {
                String t = k + "line." + j + ".";
                lines.add(new SaleLineItem_Kilpatrick(integer(p,t+"id"),get(p,t+"name"),integer(p,t+"qty"),number(p,t+"price")));
            }
            data.sales.restore(new StoreSale_Kilpatrick(integer(p,k+"id"),get(p,k+"type"),get(p,k+"customer"),get(p,k+"method"),
                    LocalDateTime.parse(get(p,k+"time")),lines));
        }
        int lastPaymentId = 0;
        for (int i=0; i<count(p,"payments"); i++) {
            String k = "payment." + i + ".";
            Payment_Kilpatrick a = new Payment_Kilpatrick(integer(p,k+"id"),get(p,k+"type"),integer(p,k+"ref"),get(p,k+"customer"),
                    number(p,k+"amount"),get(p,k+"method"),LocalDateTime.parse(get(p,k+"time")));
            if (a.id <= lastPaymentId) throw new IllegalArgumentException("Invalid payment IDs.");
            lastPaymentId = a.id;
            data.payments.add(a);
        }
        validateLedger(data);
        return data;
    }
    private static void validateLedger(FarmData_Kilpatrick data) {
        // Reject damaged or mismatched ledgers instead of silently starting over.
        for (Payment_Kilpatrick p : data.payments) {
            if (p.type.startsWith("Service")) {
                if (data.services.findById(p.referenceId) == null) throw new IllegalArgumentException("Payment references a missing appointment.");
            } else {
                boolean found = false;
                for (StoreSale_Kilpatrick s : data.sales.getSalesHistory())
                    if (s.getId() == p.referenceId && s.getType().equals(p.type)) found = true;
                if (!found) throw new IllegalArgumentException("Payment references a missing sale.");
            }
        }
        for (StoreSale_Kilpatrick s : data.sales.getSalesHistory()) {
            int matches = 0;
            long total = 0;
            for (Payment_Kilpatrick p : data.payments) if (!p.type.startsWith("Service") && p.referenceId == s.getId()) {
                matches++; total += p.amountCents;
            }
            if (matches != 1 || total != s.getTotalCents()) throw new IllegalArgumentException("Sale/payment totals disagree.");
        }
        for (ServiceAppointment_Johnson original : data.services.getAllAppointments()) {
            Appointment_Kilpatrick a = (Appointment_Kilpatrick)original;
            long paid = 0;
            for (Payment_Kilpatrick p : data.payments) if (p.type.startsWith("Service") && p.referenceId == a.getId()) paid += p.amountCents;
            if (paid != a.getPaidCents()) throw new IllegalArgumentException("Appointment/payment totals disagree.");
        }
    }
    public static FarmData_Kilpatrick load(Path file) throws IOException {
        if (Files.size(file) > 32L * 1024 * 1024) throw new IOException("Data file exceeds the 32 MB classroom-app limit.");
        Properties p = new Properties();
        try (Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) { p.load(reader); }
        try { return decode(p); }
        catch (RuntimeException ex) { throw new IOException("The saved data is invalid: " + ex.getMessage(), ex); }
    }
    public static void save(Path file, FarmData_Kilpatrick data) throws IOException {
        Path parent = file.toAbsolutePath().getParent();
        Files.createDirectories(parent);
        Path temp = Files.createTempFile(parent, "barnyard-", ".tmp");
        try {
            try (Writer writer = Files.newBufferedWriter(temp, StandardCharsets.UTF_8)) {
                encode(data).store(writer, "Barnyard Supply & Services - schema 1 - money stored in cents");
            }
            if (Files.exists(file)) Files.copy(file, file.resolveSibling(file.getFileName() + ".bak"), StandardCopyOption.REPLACE_EXISTING);
            try { Files.move(temp, file, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING); }
            catch (AtomicMoveNotSupportedException ex) { Files.move(temp, file, StandardCopyOption.REPLACE_EXISTING); }
        } finally { Files.deleteIfExists(temp); }
    }
}
