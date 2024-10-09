/**
 * Package Declaration
 */
package isf.controls.plaf;

/**
 * Java Imports
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicArrowButton;

/**
 * Description:  This class extends the MetalScrollButton to provide the custom look and feel<p>
 * User: ravishankar
 * Date: Aug 9, 2003
 */
public class IPScrollButton extends BasicArrowButton {

    private static Color shadowColor;
    private static Color highlightColor;
    private boolean isFreeStanding = false;
    private int buttonWidth;
    private Color scrollButton = new Color(204, 220, 244, 255);
    private Color scrollButtonBorder3D = new Color(204, 220, 244, 255);
    private Color scrollBackGround = new Color(241, 245, 251, 255);

    /**
     * Constructor with parameters
     *
     * @param direction    value of Direction
     * @param width        Values of Width
     * @param freeStanding Boolean value of stand
     */
    public IPScrollButton(int direction, int width, boolean freeStanding) {
        super(direction);
        shadowColor = UIManager.getColor("ScrollBar.darkShadow");
        highlightColor = UIManager.getColor("ScrollBar.highlight");
        buttonWidth = width;
        isFreeStanding = freeStanding;
    }

    /**
     * Calls the super classes paint(Graphics g) method and then paints two gradients.
     * The direction of the gradients depends on the direction of the scrollbar.
     *
     * @param g Graphics
     */
    public void paint(Graphics g) {
        boolean isPressed = getModel().isPressed();
        boolean isEnabled = getParent().isEnabled();
        boolean leftToRight = getComponentOrientation().isLeftToRight();
        Color arrowColor = Color.black;
        int width = getWidth();
        int height = getHeight();
        Graphics2D g2D = (Graphics2D) g;
        if (isPressed) {
            g2D.setColor(Color.yellow);
        } else {
            g2D.setColor(scrollButton);
        }
        g.setColor(new Color(233, 230, 222, 255));
        g.fillRect(1, 1, width - 2, height - 2);
        g.setColor(new Color(96, 96, 92));
        g.drawRoundRect(0, 0, width - 1, height - 1, 3, 3);
        int w = width;
        int h = height;
        int arrowHeight = (height + 1) / 4;
        int arrowWidth = (height + 1) / 2;
        if (getDirection() == NORTH) {
            if (!isFreeStanding) {
                height += 1;
                g.translate(0, -1);
                if (!leftToRight) {
                    width += 1;
                    g.translate(-1, 0);
                } else {
                    width += 2;
                }
            }

            // Draw the arrow
            g.setColor(arrowColor);
            int startY = ((h + 1) - arrowHeight) / 2;
            int startX = (w / 2);
            //
            for (int line = 0; line < arrowHeight; line++) {
                g.drawLine(startX - line, startY + line, startX + line + 1, startY + line);
            }
            if (!isFreeStanding) {
                height -= 1;
                g.translate(0, 1);
                if (!leftToRight) {
                    width -= 1;
                    g.translate(1, 0);
                } else {
                    width -= 2;
                }
            }
        } else if (getDirection() == SOUTH) {
            if (!isFreeStanding) {
                height += 1;
                if (!leftToRight) {
                    width += 1;
                    g.translate(-1, 0);
                } else {
                    width += 2;
                }
            }

            // Draw the arrow
            g.setColor(arrowColor);
            int startY = (((h + 1) - arrowHeight) / 2) + arrowHeight - 1;
            int startX = (w / 2);
            for (int line = 0; line < arrowHeight; line++) {
                g.drawLine(startX - line, startY - line, startX + line + 1, startY - line);
            }
            if (!isFreeStanding) {
                height -= 1;
                if (!leftToRight) {
                    width -= 1;
                    g.translate(1, 0);
                } else {
                    width -= 2;
                }
            }
        } else if (getDirection() == EAST) {
            if (!isFreeStanding) {
                height += 2;
                width += 1;
            }

            // Draw the arrow
            g.setColor(arrowColor);
            int startX = (((w + 1) - arrowHeight) / 2) + arrowHeight - 1;
            int startY = (h / 2);
            for (int line = 0; line < arrowHeight; line++) {
                g.drawLine(startX - line, startY - line, startX - line, startY + line + 1);
            }
            if (!isFreeStanding) {
                height -= 2;
                width -= 1;
            }
        } else if (getDirection() == WEST) {
            if (!isFreeStanding) {
                height += 2;
                width += 1;
                g.translate(-1, 0);
            }

            // Draw the arrow
            g.setColor(arrowColor);
            int startX = (((w + 1) - arrowHeight) / 2);
            int startY = (h / 2);
            for (int line = 0; line < arrowHeight; line++) {
                g.drawLine(startX + line, startY - line, startX + line, startY + line + 1);
            }
            if (!isFreeStanding) {
                height -= 2;
                width -= 1;
                g.translate(1, 0);
            }
        }
    }

    /**
     * set border disabled
     *
     * @param g Graphics
     * @param x value
     * @param y value
     * @param w width
     * @param h height
     */
    private void drawDisabledBorder(Graphics g, int x, int y, int w, int h) {
        g.translate(x, y);
        g.setColor(Color.black);
        //g.drawRect( 0, 0, w-1, h-1 );
    }

    /**
     * set scrollbar staand
     *
     * @param freeStanding boolean variable
     */
    public void setFreeStanding(boolean freeStanding) {
        isFreeStanding = freeStanding;
    }

    /**
     * get preferred size
     *
     * @return Dimension
     */
    public Dimension getPreferredSize() {
        if (getDirection() == NORTH) {
            return new Dimension(buttonWidth, buttonWidth - 2);
        } else if (getDirection() == SOUTH) {
            return new Dimension(buttonWidth, buttonWidth - (isFreeStanding ? 1 : 2));
        } else if (getDirection() == EAST) {
            return new Dimension(buttonWidth - (isFreeStanding ? 1 : 2), buttonWidth);
        } else if (getDirection() == WEST) {
            return new Dimension(buttonWidth - 2, buttonWidth);
        } else {
            return new Dimension(0, 0);
        }
    }

    /**
     * get Minimum Size
     *
     * @return Dimension
     */
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    /**
     * get Maximum Size
     *
     * @return Dimension
     */
    public Dimension getMaximumSize() {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    /**
     * get Button Width
     *
     * @return int
     */
    public int getButtonWidth() {
        return buttonWidth;
    }
}
