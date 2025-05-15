import java.util.*;

/**
 * Model the operation of a taxi company, operating different
 * types of vehicle. This version operates a only taxis.
 *
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29
 */
public class TaxiCompany {
    // The vehicles operated by the company.
    private final List<Vehicle> vehicles;
    private final City city;
    // Track pickups and dropoffs.
    private int totalPickups;
    private int totalDropoffs;
    // Associations between vehicles and the passengers they are to pick up.
    private final Map<Vehicle, Passenger> assignments;

    private static final int NUMBER_OF_TAXIS = 3;

    /**
     * @param city The city.
     */
    public TaxiCompany(City city) {
        if (city == null) {
            throw new IllegalArgumentException("City cannot be null");
        }
        this.city = city;
        vehicles = new LinkedList<>();
        assignments = new HashMap<>();
        setupVehicles();
    }

    public void incrementPickups() {
        totalPickups++;
    }

    public void incrementDropoffs() {
        totalDropoffs++;
    }

    public int getTotalPickups() {
        return totalPickups;
    }

    public int getTotalDropoffs() {
        return totalDropoffs;
    }

    /**
     * Return the number of taxis that currently have passengers.
     */
    public int getActiveTaxiCount() {
        int count = 0;
        for (Vehicle v : vehicles) {
            if (!v.isFree()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Request a pickup for the given passenger.
     *
     * @param passenger The passenger requesting a pickup.
     * @return Whether a free vehicle is available.
     */
    public boolean requestPickup(Passenger passenger) {
        if (passenger == null) {
            throw new IllegalArgumentException("Passenger cannot be null");
        }
        Vehicle vehicle = scheduleVehicle();
        if (vehicle != null) {
            assignments.put(vehicle, passenger);
            vehicle.setPickupLocation(passenger.getPickupLocation());
            return true;
        } else {
            return false;
        }
    }

    public int getTotalIdleSteps() {
        int total = 0;
        for (Vehicle v : vehicles) {
            if (v instanceof Taxi) {
                total += ((Taxi) v).getIdleSteps();
            }
        }
        return total;
    }
    /**
     * A vehicle has arrived at a pickup point.
     *
     * @param vehicle The vehicle at the pickup point.
     * @throws MissingPassengerException If there is no passenger waiting.
     */
    public void arrivedAtPickup(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null");
        }
        Passenger passenger = assignments.remove(vehicle);
        if (passenger == null) {
            throw new MissingPassengerException(vehicle);
        }
        city.removeItem(passenger);
        vehicle.pickup(passenger);
        incrementPickups(); // Count pickup
    }

    /**
     * A vehicle has arrived at a passenger's destination.
     *
     * @param vehicle   The vehicle at the destination.
     * @param passenger The passenger being dropped off.
     */
    public void arrivedAtDestination(Vehicle vehicle, Passenger passenger) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null");
        }
        if (passenger == null) {
            throw new IllegalArgumentException("Passenger cannot be null");
        }
        incrementDropoffs(); // Count dropoff
    }

    /**
     * @return The list of vehicles.
     */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * Find a free vehicle, if any.
     *
     * @return A free vehicle, or null if there is none.
     */
    private Vehicle scheduleVehicle() {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.isFree()) {
                return vehicle;
            }
        }
        return null;
    }

    /**
     * Set up this company's vehicles.
     * Vehicles start at random locations.
     */
    private void setupVehicles() {
        int cityWidth = city.getWidth();
        int cityHeight = city.getHeight();
        Random rand = new Random(12345);

        for (int i = 0; i < NUMBER_OF_TAXIS; i++) {
            Taxi taxi = new Taxi(this, new Location(rand.nextInt(cityWidth), rand.nextInt(cityHeight)));
            vehicles.add(taxi);
            city.addItem(taxi);
        }
    }
}
