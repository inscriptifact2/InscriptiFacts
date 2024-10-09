/**
 * Package Declaration
 */
package isf.controls.plaf;

/**
 * Java Imports
 */

import javax.swing.JScrollBar;

/**
 * Description:  This class extends the JScrollBar for providing custom look and feel<p>
 * User: ravishankar
 * Date: Aug 9, 2003
 */
public class IPScrollBar extends JScrollBar {

    /**
     * Default Constructor
     *
     * @param orientation
     */
    public IPScrollBar(int orientation) {
        super(orientation);
    }

    /**
     * set new UI for ScrollPane
     *
     * @param newUI IPScrollBarUI
     */
    public void setUI(IPScrollBarUI newUI) {
        super.setUI(newUI);
    }
}