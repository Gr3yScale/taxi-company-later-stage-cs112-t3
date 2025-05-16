public class Location {
    private final int x;
    private final int y;

    /**
     * Construct a location with specific coordinates.
     *
     * @param x The x-coordinate (must be non-negative).
     * @param y The y-coordinate (must be non-negative).
     * @throws IllegalArgumentException If either coordinate is negative.
     */
    public Location(int x, int y) {
        if (x < 0) {
            throw new IllegalArgumentException("Negative x-coordinate: " + x);
        }
        if (y < 0) {
            throw new IllegalArgumentException("Negative y-coordinate: " + y);
        }
        this.x = x;
        this.y = y;
    }

    /**
     * Calculate the next location in a direct line towards a destination.
     * Movement is limited to one step in either direction.
     *
     * @param destination The target location (must not be null).
     * @return A new location one step closer to the destination.
     */
    public Location nextLocation(Location destination) {
        if (destination == null) {
            throw new IllegalArgumentException("Destination is null");
        }

        int destX = destination.getX();
        int destY = destination.getY();

        int offsetX = Integer.compare(destX, x); // 1, 0, or -1
        int offsetY = Integer.compare(destY, y); // 1, 0, or -1

        if (offsetX != 0 || offsetY != 0) {
            return new Location(x + offsetX, y + offsetY);
        } else {
            return destination;
        }
    }

    /**
     * Calculate the number of steps required to move from this location
     * to the destination. Diagonal movement is allowed.
     *
     * @param destination The target location (must not be null).
     * @return The number of steps needed to reach the destination.
     */
    public int distance(Location destination) {
        if (destination == null) {
            throw new IllegalArgumentException("Destination is null");
        }

        int xDist = Math.abs(destination.getX() - x);
        int yDist = Math.abs(destination.getY() - y);
        return Math.max(xDist, yDist);
    }

    /**
     * Compare this location to another object for equality.
     *
     * @param other The object to compare against.
     * @return true if the object is a Location with the same coordinates.
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Location) {
            Location otherLocation = (Location) other;
            return x == otherLocation.getX() && y == otherLocation.getY();
        }
        return false;
    }

    /**
     * @return A string representation of the location in the format "location x,y".
     */
    @Override
    public String toString() {
        return "location " + x + "," + y;
    }

    /**
     * Generate a unique hash code based on the (x, y) coordinates.
     *
     * @return A hash code for the location.
     */
    @Override
    public int hashCode() {
        return (y << 16) + x;
    }

    /**
     * @return The x-coordinate of the location.
     */
    public int getX() {
        return x;
    }

    /**
     * @return The y-coordinate of the location.
     */
    public int getY() {
        return y;
    }
}
