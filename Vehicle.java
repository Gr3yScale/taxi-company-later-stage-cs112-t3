public abstract class Vehicle implements Actor {
    private TaxiCompany company;
    private Location location;
    private Location targetLocation;
    private int idleCount;
    private int pickupTravelTime;
    private int destinationTravelTime;
    private boolean headingForPickup;

    /**
     * Create a new vehicle for the given company at the specified location.
     *
     * @param company  The company operating this vehicle. Must not be null.
     * @param location The starting location of this vehicle. Must not be null.
     * @throws NullPointerException if company or location is null.
     */
    public Vehicle(TaxiCompany company, Location location) {
        if (company == null) {
            throw new NullPointerException("company");
        }
        if (location == null) {
            throw new NullPointerException("location");
        }
        this.company = company;
        this.location = location;
        targetLocation = null;
        idleCount = 0;
    }

    /**
     * Notify the company that this vehicle has arrived at a pickup location.
     */
    public void notifyPickupArrival() {
        company.arrivedAtPickup(this);
    }

    /**
     * Notify the company that this vehicle has arrived at a passenger's destination.
     *
     * @param passenger The passenger being dropped off. Must not be null.
     * @throws NullPointerException if passenger is null.
     */
    public void notifyPassengerArrival(Passenger passenger) {
        if (passenger == null) {
            throw new NullPointerException("passenger");
        }
        company.arrivedAtDestination(this, passenger);
    }

    /**
     * Assign a pickup location to this vehicle.
     * How the vehicle handles this is defined by the subclass.
     *
     * @param location The pickup location.
     */
    public abstract void setPickupLocation(Location location);

    /**
     * Receive a passenger.
     * How the vehicle handles this is defined by the subclass.
     *
     * @param passenger The passenger to pick up.
     */
    public abstract void pickup(Passenger passenger);

    /**
     * Check whether the vehicle is free.
     *
     * @return true if the vehicle is available, false otherwise.
     */
    public abstract boolean isFree();

    /**
     * Attempt to offload any passenger(s) whose destination matches the current location.
     */
    public abstract void offloadPassenger();

    /**
     * Get the current location of this vehicle.
     *
     * @return The current location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Set the current location of this vehicle.
     *
     * @param location The new location. Must not be null.
     * @throws NullPointerException if location is null.
     */
    public void setLocation(Location location) {
        if (location != null) {
            this.location = location;
        } else {
            throw new NullPointerException();
        }
    }

    /**
     * Get the target location this vehicle is heading toward.
     *
     * @return The target location, or null if the vehicle is idle.
     */
    public Location getTargetLocation() {
        return targetLocation;
    }

    /**
     * Set a new target location for the vehicle.
     *
     * @param location The destination. Must not be null.
     * @throws NullPointerException if location is null.
     */
    public void setTargetLocation(Location location) {
        if (location != null) {
            targetLocation = location;
        } else {
            throw new NullPointerException();
        }
    }

    /**
     * Clear the target location (vehicle becomes idle).
     */
    public void clearTargetLocation() {
        targetLocation = null;
    }

    /**
     * Get the number of steps this vehicle has been idle.
     *
     * @return The idle step count.
     */
    public int getIdleCount() {
        return idleCount;
    }

    /**
     * Increment the count of idle steps for this vehicle.
     */
    public void incrementIdleCount() {
        idleCount++;
    }
}
