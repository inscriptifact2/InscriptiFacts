package isf.controls.view.listeners;

import java.awt.event.KeyEvent;

/**
 * Created by IntelliJ IDEA.
 * User: kamesh
 * Date: Jun 11, 2004
 * Time: 11:54:01 PM
 * To change this template use Options | File Templates.
 */
public interface KeyHandler {
    public boolean trapKeys();

    public void fireKeyEvent(KeyEvent event);
}
