package isf.controls.plaf;

/**
 * Description:  This class provides the look and feel for the ScrollBar Button <p>
 * User: ravishankar
 * Date: Aug 9, 2003
 */

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.plaf.metal.MetalScrollButton;

public class IPScrollButtonUI extends MetalScrollButton {

    private Color col1 = new Color(235, 224, 196, 64);

    /**
     * Constructor with paramters
     *
     * @param direction    valueof direction
     * @param width        value of width
     * @param freeStanding stand of scroll
     */
    public IPScrollButtonUI(int direction, int width, boolean freeStanding) {
        super(direction, width, freeStanding);
    }

    /**
     * Calls the super classes paint(Graphics g) method and then paints two gradients.
     * The direction of the gradients depends on the direction of the scrollbar.
     *
     * @param g Graphics
     */
    public void paint(Graphics g) {
        super.paint(g);
    }
}
