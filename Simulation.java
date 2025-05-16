import java.util.LinkedList;
import java.util.List;

public class Simulation {
    private final List<Actor> actors;
    private int step;

    /**
     * Construct the simulation and initialize its components.
     * Sets up the city, taxi company, passenger source, and GUI.
     */
    public Simulation() {
        actors = new LinkedList<>();
        step = 0;
        City city = new City();
        TaxiCompany company = new TaxiCompany(city);
        PassengerSource source = new PassengerSource(city, company);

        // Register vehicles, passenger source, and GUI as actors
        actors.addAll(company.getVehicles());
        actors.add(source);
        actors.add(new CityGUI(city, company, source));
    }

    /**
     * Run the simulation for a fixed number of steps.
     * Delays between steps to allow the GUI to update.
     */
    public void run() {
        for (int i = 0; i < 5000; i++) {
            step++;
            step();
            wait(100);
        }
    }

    /**
     * Take a single step in the simulation by allowing each actor to act.
     */
    public void step() {
        for (Actor actor : actors) {
            actor.act();
        }
    }

    /**
     * Pause execution for a short time to simulate delay and allow GUI updates.
     *
     * @param milliseconds The duration to wait in milliseconds.
     */
    private void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            // Interrupted while sleeping — ignore.
        }
    }
}
