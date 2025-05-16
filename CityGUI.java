import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

public class CityGUI extends JFrame implements Actor {
    // GUI dimensions
    public static final int CITY_VIEW_WIDTH = 600;
    public static final int CITY_VIEW_HEIGHT = 600;

    private final City city;
    private final TaxiCompany company;
    private final PassengerSource passengerSource;
    private final CityView cityView;

    // Label for displaying simulation statistics
    private final JLabel statsLabel;

    /**
     * Create the CityGUI window and display the city state.
     *
     * @param city             The city being simulated.
     * @param company          The taxi company operating in the city.
     * @param passengerSource  The source of passenger creation and stats.
     */
    public CityGUI(City city, TaxiCompany company, PassengerSource passengerSource) {
        if (city == null) throw new NullPointerException("City cannot be null");
        if (company == null) throw new NullPointerException("Company cannot be null");
        if (passengerSource == null) throw new NullPointerException("PassengerSource cannot be null");

        this.city = city;
        this.company = company;
        this.passengerSource = passengerSource;

        cityView = new CityView(city.getWidth(), city.getHeight());
        statsLabel = new JLabel("Pickups: 0  |  Dropoffs: 0");
        statsLabel.setFont(new Font("Monospaced", Font.BOLD, 7));

        setTitle("Taxiville");
        setResizable(false);
        setSize(CITY_VIEW_WIDTH, CITY_VIEW_HEIGHT);
        getContentPane().add(cityView);
        getContentPane().add(statsLabel, BorderLayout.SOUTH);
        setVisible(true);
    }

    /**
     * Update the GUI with the current city state and statistics.
     */
    public void act() {
        cityView.preparePaint();
        Iterator<Item> items = city.getItems();

        while (items.hasNext()) {
            Item item = items.next();
            if (item instanceof DrawableItem drawable) {
                Location location = item.getLocation();
                cityView.drawImage(location.getX(), location.getY(), drawable.getImage());
            }
        }

        // Update statistics
        int pickups = company.getTotalPickups();
        int dropoffs = company.getTotalDropoffs();
        int missed = passengerSource.getMissedPickups();
        int created = passengerSource.getTotalPassengersCreated();
        long activeTaxis = company.getVehicles().stream().filter(v -> !v.isFree()).count();

        statsLabel.setText(String.format(
                "Passengers Collected: %d  |  Passengers Dropped Off: %d  |  Passengers Missed: %d  |  Jobs Created: %d  |  Active Taxis: %d",
                pickups, dropoffs, missed, created, activeTaxis));

        cityView.repaint();
    }

    /**
     * Component for graphically displaying the city grid.
     */
    private class CityView extends JPanel {
        private final int VIEW_SCALING_FACTOR = 6;

        private int cityWidth, cityHeight;
        private int xScale, yScale;
        private Dimension size;
        private Graphics g;
        private Image cityImage;

        /**
         * Create a new CityView panel.
         *
         * @param width  Width of the city grid.
         * @param height Height of the city grid.
         */
        public CityView(int width, int height) {
            cityWidth = width;
            cityHeight = height;
            setBackground(Color.white);
            size = new Dimension(0, 0);
        }

        /**
         * Return the preferred size of this component.
         */
        public Dimension getPreferredSize() {
            return new Dimension(cityWidth * VIEW_SCALING_FACTOR,
                    cityHeight * VIEW_SCALING_FACTOR);
        }

        /**
         * Prepare for painting the city grid. Recompute scale if resized.
         */
        public void preparePaint() {
            if (!size.equals(getSize())) {
                size = getSize();
                cityImage = createImage(size.width, size.height);
                g = cityImage.getGraphics();

                xScale = size.width / cityWidth;
                if (xScale < 1) xScale = VIEW_SCALING_FACTOR;

                yScale = size.height / cityHeight;
                if (yScale < 1) yScale = VIEW_SCALING_FACTOR;
            }

            // Clear background and draw grid
            g.setColor(Color.white);
            g.fillRect(0, 0, size.width, size.height);
            g.setColor(Color.gray);
            for (int i = 0, x = 0; x < size.width; i++, x = i * xScale) {
                g.drawLine(x, 0, x, size.height - 1);
            }
            for (int i = 0, y = 0; y < size.height; i++, y = i * yScale) {
                g.drawLine(0, y, size.width - 1, y);
            }
        }

        /**
         * Draw an image at the specified grid location.
         *
         * @param x     X position in the city grid.
         * @param y     Y position in the city grid.
         * @param image The image to draw.
         */
        public void drawImage(int x, int y, Image image) {
            g.drawImage(image, x * xScale + 1, y * yScale + 1,
                    xScale - 1, yScale - 1, this);
        }

        /**
         * Paint the city view on the screen.
         *
         * @param g The graphics context.
         */
        public void paintComponent(Graphics g) {
            if (cityImage != null) {
                Dimension currentSize = getSize();
                if (size.equals(currentSize)) {
                    g.drawImage(cityImage, 0, 0, null);
                } else {
                    // Rescale image to fit new component size
                    g.drawImage(cityImage, 0, 0, currentSize.width, currentSize.height, null);
                }
            }
        }
    }
}
