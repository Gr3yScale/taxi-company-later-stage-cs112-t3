import java.util.*;

public class TaxiCompany {
    // List of vehicles operated by the company.
    private final List<Vehicle> vehicles;
    private final City city;

    // Statistics for pickups and dropoffs.
    private int totalPickups;
    private int totalDropoffs;

    // Maps vehicles to their assigned passengers.
    private final Map<Vehicle, Passenger> assignments;

    private static final int NUMBER_OF_TAXIS = 3;

    /**
     * Create a TaxiCompany operating in the given city.
     *
     * @param city The city where the company operates.
     * @throws IllegalArgumentException if city is null.
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
     * @return The number of taxis currently carrying passengers.
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
     * @param passenger The passenger requesting pickup.
     * @return true if a free vehicle was assigned, false otherwise.
     * @throws IllegalArgumentException if passenger is null.
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
        }
        return false;
    }

    /**
     * @return The total number of idle steps for all taxis.
     */
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
     * Called when a vehicle arrives at a pickup location.
     *
     * @param vehicle The vehicle that has arrived.
     * @throws IllegalArgumentException if vehicle is null.
     * @throws MissingPassengerException if there is no passenger to pick up.
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
        incrementPickups();
    }

    /**
     * Called when a vehicle arrives at a passenger's destination.
     *
     * @param vehicle   The vehicle that has arrived.
     * @param passenger The passenger being dropped off.
     * @throws IllegalArgumentException if either parameter is null.
     */
    public void arrivedAtDestination(Vehicle vehicle, Passenger passenger) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null");
        }
        if (passenger == null) {
            throw new IllegalArgumentException("Passenger cannot be null");
        }
        incrementDropoffs();
    }

    /**
     * @return The list of vehicles operated by the company.
     */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * Find and return a free vehicle.
     *
     * @return A free vehicle, or null if none are available.
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
     * Set up the company's vehicles at random locations within the city.
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
