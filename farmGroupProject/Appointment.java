/**
 * Represents one scheduled service appointment (e.g. a basic vet
 * checkup for a customer's rabbit) and tracks its payment status.
 */
public class Appointment {

    /** Decision-driving status values for a scheduled service. */
    public enum Status {
        SCHEDULED, COMPLETED, CANCELLED
    }

    private static int nextId = 1;

    private int id;
    private String customerName;
    private String animalDescription;
    private ServiceType service;
    private String dateTime; // simple string slot, e.g. "09/20/2026 10:00 AM"
    private Status status;
    private double amountDue;
    private double amountPaid;

    public Appointment(String customerName, String animalDescription, ServiceType service, String dateTime) {
        this.id = nextId++;
        this.customerName = customerName;
        this.animalDescription = animalDescription;
        this.service = service;
        this.dateTime = dateTime;
        this.status = Status.SCHEDULED;
        this.amountDue = service.getBasePrice();
        this.amountPaid = 0.0;
    }

    public int getId() { return id; }
    public String getCustomerName() { return customerName; }
    public String getAnimalDescription() { return animalDescription; }
    public ServiceType getService() { return service; }
    public String getDateTime() { return dateTime; }
    public Status getStatus() { return status; }
    public double getAmountDue() { return amountDue; }
    public double getAmountPaid() { return amountPaid; }

    public void markCompleted() {
        status = Status.COMPLETED;
    }

    public void cancel() {
        status = Status.CANCELLED;
    }

    public double getBalanceDue() {
        double balance = amountDue - amountPaid;
        return balance < 0 ? 0 : balance;
    }

    /** Decision structure: distinguishes unpaid / partial / paid in full. */
    public boolean isPaidInFull() {
        return amountPaid >= amountDue;
    }

    /**
     * Records a payment toward this appointment.
     * Returns a short status message describing the result (full,
     * partial, or invalid) for the GUI to show the user.
     */
    public String recordPayment(double amount) {
        if (amount <= 0) {
            return "Payment amount must be greater than zero.";
        }
        amountPaid += amount;
        if (isPaidInFull()) {
            return "Paid in full. Thank you!";
        } else {
            return String.format("Partial payment received. Balance remaining: $%.2f", getBalanceDue());
        }
    }

    public String getDisplayInfo() {
        return String.format(
            "#%-3d %-15s Animal: %-12s Service: %-15s When: %-18s Status: %-10s Due: $%-7.2f Paid: $%-7.2f",
            id, customerName, animalDescription, service.getName(), dateTime, status, amountDue, amountPaid
        );
    }

    @Override
    public String toString() {
        return getDisplayInfo();
    }
}
