import java.util.ArrayList;
import java.util.List;

/**
 * Owns the list of available ServiceType offerings and the list of
 * scheduled Appointments. Shared by both the Service Scheduling
 * screen and the Service Payments screen, since payments are made
 * against appointments that scheduling creates.
 */
public class ServiceManager {

    private List<ServiceType> serviceTypes;
    private List<Appointment> appointments;

    public ServiceManager() {
        serviceTypes = new ArrayList<>();
        appointments = new ArrayList<>();
        seedServiceTypes();
    }

    private void seedServiceTypes() {
        serviceTypes.add(new ServiceType("Basic Vet Checkup", "General wellness exam", 35.00));
        serviceTypes.add(new ServiceType("Vaccination", "Standard vaccination", 25.00));
        serviceTypes.add(new ServiceType("Nail Trim", "Nail trimming service", 12.00));
        serviceTypes.add(new ServiceType("Grooming", "Basic grooming/bath", 20.00));
    }

    public List<ServiceType> getServiceTypes() {
        return serviceTypes;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public ServiceType findServiceTypeByName(String name) {
        for (ServiceType s : serviceTypes) {
            if (s.getName().equalsIgnoreCase(name)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Schedules a new appointment.
     * Decision structure: rejects the request if the requested date/time
     * slot is already booked with another active (non-cancelled) appointment.
     */
    public Appointment scheduleAppointment(String customerName, String animalDescription,
                                            ServiceType service, String dateTime) {
        for (Appointment a : appointments) {
            if (a.getDateTime().equalsIgnoreCase(dateTime) && a.getStatus() != Appointment.Status.CANCELLED) {
                return null; // time slot conflict
            }
        }
        Appointment appt = new Appointment(customerName, animalDescription, service, dateTime);
        appointments.add(appt);
        return appt;
    }

    public Appointment findById(int id) {
        for (Appointment a : appointments) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    /** Decision structure: only appointments with an outstanding balance are returned. */
    public List<Appointment> getUnpaidAppointments() {
        List<Appointment> unpaid = new ArrayList<>();
        for (Appointment a : appointments) {
            if (!a.isPaidInFull() && a.getStatus() != Appointment.Status.CANCELLED) {
                unpaid.add(a);
            }
        }
        return unpaid;
    }

    public String recordPayment(int appointmentId, double amount) {
        Appointment appt = findById(appointmentId);
        if (appt == null) {
            return "No appointment found with that ID.";
        }
        return appt.recordPayment(amount);
    }

    public String getFormattedSchedule() {
        if (appointments.isEmpty()) {
            return "No appointments scheduled yet.";
        }
        StringBuilder sb = new StringBuilder();
        for (Appointment a : appointments) {
            sb.append(a.getDisplayInfo()).append("\n");
        }
        return sb.toString();
    }

    public String getFormattedServiceMenu() {
        StringBuilder sb = new StringBuilder();
        for (ServiceType s : serviceTypes) {
            sb.append(s).append("\n");
        }
        return sb.toString();
    }

    public String getFormattedUnpaidList() {
        List<Appointment> unpaid = getUnpaidAppointments();
        if (unpaid.isEmpty()) {
            return "All appointments are paid in full.";
        }
        StringBuilder sb = new StringBuilder();
        for (Appointment a : unpaid) {
            sb.append(a.getDisplayInfo()).append("  | Balance: $")
              .append(String.format("%.2f", a.getBalanceDue())).append("\n");
        }
        return sb.toString();
    }
}
