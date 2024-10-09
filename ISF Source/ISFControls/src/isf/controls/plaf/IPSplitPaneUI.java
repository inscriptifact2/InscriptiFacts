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
import javax.swing.JComponent;
import javax.swing.JSplitPane;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

/**
 * Description:  This class provides the look and feel for the SplitPaner <p>
 * User: ravishankar
 * Date: Aug 9, 2003
 */
public class IPSplitPaneUI extends BasicSplitPaneUI {

    /**
     * Default Constructor
     */
    public IPSplitPaneUI() {
        super();
    }

    /**
     * Creates Component UI for given component
     *
     * @param x JComponent
     * @return ComponentUI
     */
    public static ComponentUI createUI(JComponent x) {
        return new IPSplitPaneUI();
    }

    /**
     * Set defaults values and state to the component
     */
    protected void installDefaults() {
        super.installDefaults();
        ((IPSplitPaneDivider) divider).setIPSplitPaneUI(this);
    }

    /**
     * Creates BasicSplitPaneDivider
     *
     * @return BasicSplitPaneDivider
     */
    public BasicSplitPaneDivider createDefaultDivider() {
        return new IPSplitPaneDivider(this);
    }


    public IPSplitPaneDivider getIsfDivider() {
        return (IPSplitPaneDivider) divider;
    }

    /**
     * get Divider borderSize of the splitpane
     *
     * @return
     */
    protected int getDividerBorderSize() {
        return 0;
    }

    /**
     * This is used to paint the children of the splitpane component
     *
     * @param jc
     * @param g
     */
    public void finishedPaintingChildren(JSplitPane jc, Graphics g) {
        Dimension size = splitPane.getSize();
        if (jc == splitPane && getLastDragLocation() != -1 &&
                !isContinuousLayout() && !draggingHW) {
            int center = getLastDragLocation();
            g.setColor(Color.black);
            if (getOrientation() == JSplitPane.HORIZONTAL_SPLIT) {
                g.drawLine(center, 0, center, size.height - 1);
            } else {
                g.drawLine(0, center, size.width - 1, center);
            }
        }
        int x = divider.getLocation().x;
        int y = divider.getLocation().y;
        if (getOrientation() == JSplitPane.HORIZONTAL_SPLIT) {
            IPSplitPaneDivider.paintTop(g, x);
            IPSplitPaneDivider.paintBottom(g, x, size.height - 1);
        } else {
            IPSplitPaneDivider.paintLeft(g, y);
            IPSplitPaneDivider.paintRight(g, y, size.width - 1);
        }
    }


    protected void finishDraggingTo(int location) {

    }

    protected void dragDividerTo(int location) {

    }

    protected void startDragging() {

    }

}
