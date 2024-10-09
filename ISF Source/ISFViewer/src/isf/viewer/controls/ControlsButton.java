package isf.viewer.controls;

import isf.controls.controllers.PTMFrameManager;
import isf.controls.utils.CursorManager;
import isf.controls.utils.GlassPaneManager;
import isf.controls.view.controls.ISFButton;
import isf.viewer.containers.ButtonPopUp;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by IntelliJ IDEA.
 * User: 0223
 * Date: Aug 4, 2007
 * Time: 5:48:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class ControlsButton extends ISFButton implements ActionListener {

    private ButtonPopUp popup;
    private boolean showOnRight;

    public ControlsButton(String ac, String eicon, String disabledIcon, ButtonPopUp fc) {
        super(ac, eicon, disabledIcon);
        popup = fc;
        addActionListener(this);
        showOnRight = false;
    }

    public ControlsButton(String ac, String eicon, String disabledIcon, ButtonPopUp fc, boolean showOnRight) {
        super(ac, eicon, disabledIcon);
        popup = fc;
        this.showOnRight = showOnRight;
        addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (!GlassPaneManager.getInstance().isShowing()) {

            popup.init();
            Dimension size = popup.getPreferredSize();
            int x, y;

            Point p = getLocationOnScreen();
            Rectangle r = getBounds();
            Rectangle frame = CursorManager.getFrameBounds();
           // System.out.println("p:"+p+"::r:"+r+"::frame:"+frame);
            y = (int) (p.y + (r.getHeight()- frame.getY()) - 20);
            //Rectangle target =null;
            if (!showOnRight) {
                x = (int) (r.getWidth()- frame.getX()) + p.x - size.width;
            } else {
                Rectangle l = getRootPane().getParent().getBounds();
                x = l.x + l.width - size.width;
            }
            GlassPaneManager.getInstance().showComponent(popup, new Rectangle(x, y, size.width, size.height));
        } else {
            popup.releaseControls();
            GlassPaneManager.getInstance().hideAll();
            PTMFrameManager.getInstance().releaseChangeListeners();
        }

    }
}
