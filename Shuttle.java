import java.util.LinkedList;
import java.util.List;

public class Shuttle extends Vehicle {
    // The list of destinations for the shuttle.
    private List<Location> destinations;
    // The list of passengers on the shuttle.
    private List<Passenger> passengers;

    /**
     * Constructor for objects of class Shuttle
     *
     * @param company  The taxi company. Must not be null.
     * @param location The vehicle's starting point. Must not be null.
     * @throws NullPointerException If company or location is null.
     */
    public Shuttle(TaxiCompany company, Location location) {
        super(company, location);
        destinations = new LinkedList<>();
        passengers = new LinkedList<>();
    }

    /**
     * Carry out a shuttle's actions.
     */
    public void act() {
    }

    /**
     * Is the shuttle free?
     *
     * @return Whether or not this vehicle is free.
     */
    public boolean isFree() {
        return true;
    }

    /**
     * Receive a pickup location.
     *
     * @param location The pickup location.
     */
    public void setPickupLocation(Location location) {
        if (location == null)
            throw new IllegalArgumentException("Location is null");
        destinations.add(location);
        chooseTargetLocation();
    }

    /**
     * Receive a passenger.
     * Add their destination to the list.
     *
     * @param passenger The passenger.
     */
    public void pickup(Passenger passenger) {
        if (passenger == null)
            throw new IllegalArgumentException("Passenger is null");
        passengers.add(passenger);
        destinations.add(passenger.getDestination());
        chooseTargetLocation();
    }

    /**
     * Decide where to go next, based on the list of
     * possible destinations.
     */
    private void chooseTargetLocation() {
    }

    /**
     * Offload a passenger whose destination is the
     * current location.
     */
    public void offloadPassenger() {
    }
}
