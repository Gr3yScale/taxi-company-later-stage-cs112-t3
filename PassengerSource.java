import java.util.Random;

public class PassengerSource implements Actor {
    private final City city;
    private final TaxiCompany company;
    private final Random rand;
    private static final double CREATION_PROBABILITY = 0.06;
    private int missedPickups;
    private int totalPassengersCreated;

    /**
     * Construct a PassengerSource that will create passengers in a given city
     * and request pickups from a specified taxi company.
     *
     * @param city    The city in which passengers will be created (must not be null).
     * @param company The taxi company used for handling pickups (must not be null).
     * @throws NullPointerException if either city or company is null.
     */
    public PassengerSource(City city, TaxiCompany company) {
        if (city == null) {
            throw new NullPointerException("City must not be null.");
        }
        if (company == null) {
            throw new NullPointerException("Company must not be null.");
        }

        this.city = city;
        this.company = company;
        this.rand = new Random(12345); // Fixed seed for repeatable tests
        this.missedPickups = 0;
        this.totalPassengersCreated = 0;
    }

    /**
     * Attempt to create a new passenger. If a taxi is available, assign it to
     * the passenger and add the passenger to the city. Otherwise, increment
     * the missed pickup counter.
     */
    public void act() {
        if (rand.nextDouble() <= CREATION_PROBABILITY) {
            Passenger passenger = createPassenger();
            totalPassengersCreated++;
            if (company.requestPickup(passenger)) {
                city.addItem(passenger);
            } else {
                missedPickups++;
            }
        }
    }

    /**
     * @return The number of passengers who could not be picked up
     * due to lack of available taxis.
     */
    public int getMissedPickups() {
        return missedPickups;
    }

    /**
     * @return The total number of passengers created since the start.
     */
    public int getTotalPassengersCreated() {
        return totalPassengersCreated;
    }

    /**
     * Create a new passenger with random pickup and destination
     * locations (ensuring they are different).
     *
     * @return The newly created Passenger object.
     */
    private Passenger createPassenger() {
        int cityWidth = city.getWidth();
        int cityHeight = city.getHeight();

        Location pickupLocation = new Location(rand.nextInt(cityWidth), rand.nextInt(cityHeight));
        Location destination;

        do {
            destination = new Location(rand.nextInt(cityWidth), rand.nextInt(cityHeight));
        } while (pickupLocation.equals(destination));

        return new Passenger(pickupLocation, destination);
    }
}
