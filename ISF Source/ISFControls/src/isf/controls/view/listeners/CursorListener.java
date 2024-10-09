package isf.controls.view.listeners;

import java.awt.Cursor;
import java.awt.Rectangle;

/**
 * Created by IntelliJ IDEA.
 * User: kamesh
 * Date: Jan 12, 2004
 * Time: 10:07:36 PM
 * To change this template use Options | File Templates.
 */
public interface CursorListener {
    public void changeCursor(int c);

    public void changeCursor(Cursor r);
    public Cursor getCursor();

    public Rectangle getFrameBounds();
            

}
