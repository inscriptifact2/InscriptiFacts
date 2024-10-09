/**
 * Package Declaration
 */
package isf.controls.plaf;

/**
 * Java Imports
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.io.Serializable;
import javax.swing.Icon;
import javax.swing.JComponent;

/**
 * This utility class draws the horizontal bars which indicate a MetalComboBox
 * User: kamesh
 * Date: Aug 9, 2003
 */
public class ISFComboBoxIcon implements Icon, Serializable {

    /**
     * Paints the horizontal bars for the combo
     *
     * @param c
     * @param g
     * @param x
     * @param y
     */
    public void paintIcon(Component c, Graphics g, int x, int y) {
        JComponent component = (JComponent) c;
        int iconWidth = getIconWidth();
        g.translate(x, y);
        g.setColor(new Color(0, 40, 20));
        g.drawLine(0, 0, iconWidth - 1, 0);
        g.drawLine(1, 1, 1 + (iconWidth - 3), 1);
        g.drawLine(2, 2, 2 + (iconWidth - 5), 2);
        g.drawLine(3, 3, 3 + (iconWidth - 7), 3);
        g.drawLine(4, 4, 4 + (iconWidth - 9), 4);
        g.translate(-x, -y);
    }

    /**
     * gets the icon width
     *
     * @return int
     */
    public int getIconWidth() {
        return 8;
    }

    /**
     * gets the icon height
     *
     * @return int
     */
    public int getIconHeight() {
        return 5;
    }
}
