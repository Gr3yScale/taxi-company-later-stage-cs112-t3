import javax.swing.*;
import java.awt.*;

public class Passenger implements DrawableItem {
    private final Location pickup;
    private final Location destination;
    private final Image image;

    /**
     * Create a new Passenger with a pickup and destination location.
     *
     * @param pickup      The pickup location (must not be null).
     * @param destination The destination location (must not be null and not equal to pickup).
     * @throws NullPointerException     If either pickup or destination is null.
     * @throws IllegalArgumentException If pickup and destination are the same.
     */
    public Passenger(Location pickup, Location destination) {
        if (pickup == null) {
            throw new NullPointerException("Pickup location cannot be null");
        }
        if (destination == null) {
            throw new NullPointerException("Destination location cannot be null");
        }
        if (destination.equals(pickup)) {
            throw new IllegalArgumentException("Pickup and destination cannot be the same");
        }

        this.pickup = pickup;
        this.destination = destination;

        image = new ImageIcon(getClass().getResource("images/person.jpg")).getImage();
    }

    /**
     * Return a string representation of the passenger and their journey.
     *
     * @return A string describing the passenger's route.
     */
    public String toString() {
        return "Passenger travelling from " + pickup + " to " + destination;
    }

    /**
     * Get the passenger image used for GUI display.
     *
     * @return Image representing the passenger.
     */
    public Image getImage() {
        return image;
    }

    /**
     * Get the passenger's current location (pickup point).
     *
     * @return The pickup location.
     */
    public Location getLocation() {
        return pickup;
    }

    /**
     * Get the pickup location.
     *
     * @return The pickup location.
     */
    public Location getPickupLocation() {
        return pickup;
    }

    /**
     * Get the destination location.
     *
     * @return The destination location.
     */
    public Location getDestination() {
        return destination;
    }
}
