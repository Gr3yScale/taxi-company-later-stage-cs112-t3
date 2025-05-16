import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Taxi extends Vehicle implements DrawableItem {
    private Passenger passenger;
    private final Image emptyImage;
    private final Image passengerImage;
    private int idleSteps;

    /**
     * Create a new Taxi.
     *
     * @param company  The taxi company managing this taxi. Must not be null.
     * @param location The starting location of the taxi. Must not be null.
     * @throws NullPointerException if company or location is null.
     */
    public Taxi(TaxiCompany company, Location location) {
        super(company, location);

        emptyImage = new ImageIcon(Objects.requireNonNull(getClass().getResource(
                "images/taxi.jpg"))).getImage();

        passengerImage = new ImageIcon(Objects.requireNonNull(getClass().getResource(
                "images/taxi+person.jpg"))).getImage();
    }

    /**
     * Perform the taxi's action:
     * - Move toward a target location, if any.
     * - If free and idle, increment idle steps.
     * - Handle arrival logic for pickup or drop-off.
     */
    public void act() {
        Location target = getTargetLocation();

        if (isFree()) {
            idleSteps++;
        }

        if (target != null) {
            Location next = getLocation().nextLocation(target);
            System.out.println("Taxi moving from " + getLocation() + " to " + next);
            setLocation(next);

            if (next.equals(target)) {
                if (passenger != null) {
                    System.out.println("Arrived at passenger destination: " + target);
                    notifyPassengerArrival(passenger);
                    offloadPassenger();
                } else {
                    System.out.println("Arrived at pickup location: " + target);
                    notifyPickupArrival();
                }
            }
        } else {
            incrementIdleCount();
        }
    }

    /**
     * Check if the taxi is free (no target and no passenger).
     *
     * @return true if taxi is available, false otherwise.
     */
    public boolean isFree() {
        return getTargetLocation() == null && passenger == null;
    }

    /**
     * Assign a pickup location.
     *
     * @param location The pickup location (not null).
     * @throws IllegalArgumentException if taxi is not free or location is null.
     */
    public void setPickupLocation(Location location) {
        if (!isFree()) {
            throw new IllegalArgumentException("Cannot set pickup location when taxi is not free");
        }
        if (location == null) {
            throw new IllegalArgumentException("Pickup location cannot be null");
        }
        setTargetLocation(location);
    }

    /**
     * Load a passenger into the taxi and set their destination as the new target.
     *
     * @param passenger The passenger to pick up (not null).
     * @throws IllegalArgumentException if passenger is null.
     */
    public void pickup(Passenger passenger) {
        if (passenger == null) {
            throw new IllegalArgumentException("Passenger cannot be null");
        }
        this.passenger = passenger;
        setTargetLocation(passenger.getDestination());
    }

    /**
     * Drop off the current passenger and clear the target.
     */
    public void offloadPassenger() {
        passenger = null;
        clearTargetLocation();
    }

    /**
     * Return the appropriate image depending on taxi's state.
     *
     * @return Image of empty or occupied taxi.
     */
    public Image getImage() {
        return (passenger != null) ? passengerImage : emptyImage;
    }

    /**
     * Get the number of steps the taxi has been idle.
     *
     * @return Idle step count.
     */
    public int getIdleSteps() {
        return idleSteps;
    }

    /**
     * Return a string describing the taxi's location.
     *
     * @return A string representation of the taxi.
     */
    public String toString() {
        return "Taxi at " + getLocation();
    }
}
