/**
 * Package Declaration
 */
package isf.controls.plaf;

/**
 * Java Imports
 */

import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicScrollPaneUI;

/**
 * implementation of ScrollPaneUI.<p>
 * User: ravishankar
 * Date: Aug 9, 2003
 */
public class IPScrollPaneUI extends BasicScrollPaneUI {

    //private final static Border vsbMarginBorder = new EmptyBorder(0, 4, 0, 0);
    // private final static Border hsbMarginBorder = new EmptyBorder(4, 0, 0, 0);

    //private Border vsbBorder;
    //private Border hsbBorder;

    /**
     * Set Defaults for ScrollUI
     *
     * @param scrollpane scrollpane
     */
    protected void installDefaults(JScrollPane scrollpane) {
        super.installDefaults(scrollpane);
        JScrollBar vsb = scrollpane.getVerticalScrollBar();
        JScrollBar hsb = scrollpane.getHorizontalScrollBar();
        /*if (vsb != null) {
            vsbBorder = new CompoundBorder(vsbMarginBorder, vsb.getBorder());
            vsb.setBorder(vsbBorder);
        }

        JScrollBar hsb = scrollpane.getHorizontalScrollBar();
        if (hsb != null) {
            hsbBorder = new CompoundBorder(hsbMarginBorder, hsb.getBorder());
            hsb.setBorder(hsbBorder);
        }*/
        vsb.setBorder(null);
        hsb.setBorder(null);
    }

    /**
     * Remove Defaults for Scroll UI
     *
     * @param scrollpane scrollpane
     */
    protected void uninstallDefaults(JScrollPane scrollpane) {
        super.uninstallDefaults(scrollpane);
        JScrollBar vsb = scrollpane.getVerticalScrollBar();
        /*if (vsb != null) {
            if (vsb.getBorder() == vsbBorder) {
            vsb.setBorder(null);
            }
            vsbBorder = null;
        }

        JScrollBar hsb = scrollpane.getHorizontalScrollBar();
        if (hsb != null) {
            if (hsb.getBorder() == hsbBorder) {
            hsb.setBorder(null);
            }
            hsbBorder = null;
        }*/
    }

    /**
     * Creates Component UI for given component
     *
     * @param x JComponent
     * @return ComponentUI
     */
    public static ComponentUI createUI(JComponent x) {
        return new IPScrollPaneUI();
    }
}

