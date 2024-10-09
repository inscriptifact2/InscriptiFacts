package isf.controls.view.controls;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
//import org.apache.log4j.Logger;
//import com.isf.utils.LogManager;

/**
 * Creates an animated component that paints a -like progress bar
 *
 * @author shaines
 */
public class ISFProgressBar extends JPanel implements ActionListener {

       /**
     * SLOW: invokes updateAnimation() every 500 milliseconds
     */
    public static int SLOW = 1000;
    /**
     * The current speed of the animation
     */
    protected int speed = SLOW;
    /**
     * The thread that runs this animation
     */
    protected Timer timer;
    ;
    /**
     * Internal state: is this animated component running?
     */
    protected boolean running = false;
    /**
     * The background color of the entire component
     */
    protected Color bgColor = Color.lightGray;
    /**
     * The foreground color of the entire component
     */
    protected Color forecolor = Color.blue;
    /**
     * The background color of the bars
     */
    protected Color barBgColor = Color.white;
    /**
     * The states of each bar in the  eye
     */
    protected ArrayList bars = new ArrayList();
    /**
     * Denotes whether or not each bar's rectangle has been initialized - set the first time
     * the panel is painted
     */
    protected boolean rectsInitialized = false;
    /**
     * The current size of the panel, used to compare to the painted size to determine whether
     * or not the panel has been resized
     */
    protected Dimension size;
    final int LEFT = 0;
    final int RIGHT = 1;
    final int HIGH = 0;
    final int MED = 1;
    final int LOW = 2;
    final int OFF = -1;
    /**
     * The position of the head of the  eye, from 0 .. 9
     */
    private int pos = 0;
    /**
     * The direction that the  eye is moving, right or left
     */
    private int direction = RIGHT;
    /**
     * Denotes whether or not the  eye is running
     */
    private String message = "";
    /**
     * A back buffer, used to reduce flicker during painting
     */
    private BufferedImage backBuffer;

    /**
     * Creates a new Bar with the specified bar color
     *
     * @param barColor
     */
    public ISFProgressBar(Color barColor) {
        setBarColor(barColor);
        initializeBars();

        // define our minimum size
        this.setMinimumSize(new Dimension(200, 50));
        this.setPreferredSize(new Dimension(200, 50));
    }

    /**
     * Returns the background color for the Bar component
     *
     * @return
     */
    public Color getBackgroundColor() {
        return bgColor;
    }

    public boolean isRunning() {
        return running;
    }

    /**
     * Set the lable text
     *
     * @return
     */
    public void setText(String text) {
        message = text;
        // Dimension size = getPreferredSize();
        if (size != null) {
            repaint(10, size.height - 45, size.width - 10, 45);
        }
    }

    /**
     * Sets the background color for the Bar component
     *
     * @param bgColor
     */
    public void setBackgroundColor(Color bgColor) {
        this.bgColor = bgColor;
    }

    /**
     * Sets the forground color for the Bar component
     *
     * @param bgColor
     */
    public void setForegroundColor(Color foreColor) {
        this.forecolor = foreColor;
    }

    /**
     * Returns the background color of the bar in the Bar component
     *
     * @return
     */
    public Color getBarBackgroundColor() {
        return barBgColor;
    }

    /**
     * Sets the background color of the bar in the Bar Component
     *
     * @param barBgColor
     */
    public void setBarBackgroundColor(Color barBgColor) {
        this.barBgColor = barBgColor;
    }
    private HashMap colorMap = new HashMap();

    /**
     * Sets the color of the bar, where the color represents the HIGH state and the MEDIUM and LOW
     * states are obtained by calling getLighterColor() on the specified color
     *
     * @param color
     */
    public void setBarColor(Color color) {
        colorMap.put(new Integer(HIGH), color);
        colorMap.put(new Integer(MED), getLighterColor(color));
        colorMap.put(new Integer(LOW), getLighterColor((Color) colorMap.get(new Integer(MED))));
    }

    /**
     * Makes the color lighter by increasing its Red, Green, and Blue values by 40 and stopping at 255
     *
     * @param color The color to make lighter
     * @return Returns a lighter copy of the color
     */
    protected Color getLighterColor(Color color) {
        int r = color.getRed() + 60;
        int g = color.getGreen() + 60;
        int b = color.getBlue() + 60;

        return new Color(r <= 255 ? r : 255,
                g <= 255 ? g : 255,
                b <= 255 ? b : 255);

    }

    /**
     * Paints the  bar
     */
    public void paint(Graphics g) {
        // Get the size of our component
        Dimension currentSize = getSize();

        if (size == null || backBuffer == null || currentSize.width != size.width || currentSize.height != size.height) {
            // We have to create a new back buffer if this is the first time we're running, one does not exist
            // or we've resized...
            backBuffer = new BufferedImage(currentSize.width, currentSize.height, BufferedImage.TYPE_USHORT_565_RGB);
        }
        Graphics2D g2 = (Graphics2D) backBuffer.getGraphics();

        // Fill in the background
        g2.setColor(bgColor);
        g2.fillRect(0, 0, currentSize.width, currentSize.height);

        // See if we need to recompute the rectangles that host our  bars
        if (!rectsInitialized || size == null || currentSize.width != size.width || currentSize.height != size.height) {
            // Update our size
            size = currentSize;

            // We want 10 bars so divide the width (minus buffer) by 10, including buffering between each bar and on the ends
            int segmentWidth = (size.width - 20) / 10;
            int spacerWidth = (int) ((double) segmentWidth * 0.20d);
            int barWidth = segmentWidth - spacerWidth;

            // Observe our 10 pixel buffer
            int x = 10;

            // Compute the height
            int y = (int) (size.height / 10.0);
            int barHeight = (int) (size.height * 0.15d);

            // We have to build the rectangles for each of our bars, either because they
            // have not been initialized or because the panel has resized
            for (int i = 0; i < 10; i++) {
                Bar bar = (Bar) bars.get(i);
                bar.setRect(new Rectangle(x + (i * barWidth) + (i * spacerWidth), y, barWidth, barHeight));
            }

            // Reset these state variables
            rectsInitialized = true;
        }

        // Draw the bars
        for (int i = 0; i < bars.size(); i++) {
            Bar bar = (Bar) bars.get(i);
            drawBar(g2, getStateColor(bar.getState()), bar.getRect());
        }
        if (!message.equalsIgnoreCase("")) {
            g2.setColor(forecolor);
            g2.drawString(message, 10, size.height - 25);
        }

        // Blit the backbuffer to the graphics display
        g.drawImage(backBuffer, 0, 0, size.width, size.height, null);
    }

    /**
     * Returns the color that corresponds to the specified state
     *
     * @param state
     * @return
     */
    private Color getStateColor(int state) {
        if (state == OFF) {
            return barBgColor;
        } else {
            return (Color) colorMap.get(new Integer(state));
        }
    }

    /**
     * Helper method that draws an individual  bar
     *
     * @param g
     * @param c
     * @param rect
     */
    protected void drawBar(Graphics2D g, Color c, Rectangle rect) {
        drawBar(g, c, rect.x, rect.y, rect.width, rect.height);
    }

    /**
     * Helper method that draws an individual  bar
     *
     * @param g      The graphics context
     * @param c      The color of the bar
     * @param x      The x-coordinate
     * @param y      The y-coordinate
     * @param width  The width of the bar
     * @param height The height of the bar
     */
    protected void drawBar(Graphics2D g, Color c, int x, int y, int width, int height) {
        // Set our color
        g.setColor(c);

        // Draw our bar
        g.fillRect(x, y, width, height);
    }

    /**
     * Determine which bars need to be repainted
     */
    public void updateAnimation() {
        // Invoke the algorithm for updating the  bars
        traditional();

        // Intelligently determine what to repaint
        boolean repaintAll = false;
        int x = -1;
        int y = -1;
        int x2 = -1;
        int y2 = -1;
        for (int i = 0; i < bars.size(); i++) {
            Bar bar = (Bar) bars.get(i);
            if (bar.isStateModified()) {
                // Update our coordinates
                Rectangle rect = bar.getRect();
                if (rect == null) {
                    repaintAll = true;
                } else {
                    if (x == -1 || rect.x < x) {
                        x = rect.x;
                    }
                    if (y == -1 || rect.y < y) {
                        y = rect.y;
                    }
                    if (x2 == -1 || rect.x + rect.width > x2) {
                        x2 = rect.x + rect.width;
                    }
                    if (y2 == -1 || rect.y + rect.height > y2) {
                        y2 = rect.y + rect.height;
                    }
                }

                // Reset this bar's modified state
                bar.setStateModified(false);
            }
        }

        if (repaintAll) {
            // Repaint the entire panel
            repaint();
        } else {
            // Repaint only the area that needs to be repainted
            repaint(x, y, x2 - x, y2 - y);
        }
    }

    /**
     * We override the stopAnimation() method so that we can reset the state of the  bars
     */
    public void stopAnimation() {
        running = false;
        if(timer!=null)
             timer.stop();
        timer = null;
        //System.out.println("vcalled stop:" + message);
        // Reset the bars
        for (int i = 0; i < bars.size(); i++) {
            Bar bar = (Bar) bars.get(i);
            bar.setState(OFF);
        }

        // Repaint the component with all of the bars in the OFF state
        repaint();
    }

    /**
     * Creates the bar list and initializes all bars to an OFF state
     */
    private void initializeBars() {
        // Delete any existing states
        if (bars.size() > 0) {
            bars.clear();
        }

        // Turn off all eyes
        for (int i = 0; i < 10; i++) {
            bars.add(new Bar(OFF));
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(200, 50);
    }

    /**
     * Implements a traditional  eye algorithm
     */
    private void traditional() {
        // Adjust the position of the eye
        if (direction == RIGHT) {
            // Handle moving right
            if (pos < 9) {
                pos++;
            } else {
                direction = LEFT;
            }
        } else {
            // Handle moving left
            if (pos > 0) {
                pos--;
            } else {
                direction = RIGHT;
            }
        }

        // Update the states of the bars based on the position
        for (int i = 0; i < 10; i++) {
            if (pos == i) {
                ((Bar) bars.get(i)).setState(HIGH);
            } else if ((direction == RIGHT && i == pos - 1) ||
                    (direction == LEFT && i == pos + 1)) {
                ((Bar) bars.get(i)).setState(MED);
            } else if ((direction == RIGHT && i == pos - 1) ||
                    (direction == LEFT && i == pos + 1)) {
                ((Bar) bars.get(i)).setState(MED);
            } else if ((direction == RIGHT && i == pos - 2) ||
                    (direction == LEFT && i == pos + 2)) {
                ((Bar) bars.get(i)).setState(LOW);
            } else {
                ((Bar) bars.get(i)).setState(OFF);
            }
        }
    }

 

    /**
     * Starts the animation
     */
    public void startAnimation() {
        //thread.start();
        timer = new Timer(speed, this);
        timer.setInitialDelay(0);
        timer.setCoalesce(true);
        timer.start();
        running = true;
       
    }

    /**
     * Sets the animation speed, which is defined as the number of milliseconds between animation
     * updates. Therefore, smaller = faster, larger = slower. If you need a guide, you can use one of
     * the predefined constants: SLOW, MEDIUM_SLOW, MEDIUM, MEDIUM_FAST, or FAST.
     *
     * @param speed
     */
    public void setAnimationSpeed(int speed) {
        this.speed = speed;
    }

    public void actionPerformed(ActionEvent e) {
        if (running) {

            updateAnimation();
        }
       // log.debug(message);


    }

    class Bar {

        private int state;
        private Rectangle rect;
        private boolean stateModified;
        private boolean sizeModified;

        public Bar(int state) {
            this.state = state;
        }

        public boolean isRectDefined() {
            return rect != null;
        }

        public void setRect(Rectangle rect) {
            this.rect = rect;
        }

        public Rectangle getRect() {
            return rect;
        }

        public boolean isStateModified() {
            return stateModified;
        }

        public void setStateModified(boolean stateModified) {
            this.stateModified = stateModified;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            if (this.state != state) {
                stateModified = true;
                this.state = state;
            }
        }

        public void setSizeModified() {
            sizeModified = true;
        }

        public boolean isSizeModified() {
            return sizeModified;
        }
    }
}