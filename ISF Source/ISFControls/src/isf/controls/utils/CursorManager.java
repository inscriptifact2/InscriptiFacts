/**
 * Created by IntelliJ IDEA.
 * User: kamesh
 * Date: Jan 12, 2004
 * Time: 10:03:31 PM
 * To change this template use Options | File Templates.
 */
package isf.controls.utils;

import isf.controls.view.listeners.CursorListener;
import java.awt.Cursor;
import java.awt.Rectangle;

public class CursorManager {
    private static CursorManager ourInstance;
    public static boolean wait;
    private static CursorListener cl;

    public synchronized static CursorManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new CursorManager();
        }
        return ourInstance;
    }

    private CursorManager() {
    }

    public void addCursorListener(CursorListener cl) {
        this.cl = cl;
    }

    public static void changeCursor(int c) {
        
        if (c == 3)
            wait = true;
        else
            wait = false;
		if (cl != null)
            cl.changeCursor(c);
        
    }

    public static void changeCursor(Cursor c) {
        if (cl != null)
            cl.changeCursor(c);
    }
    
    public static  Cursor getCursor()
    {
        return cl.getCursor();
    }

    public static Rectangle getFrameBounds()
    {
        if(cl!=null)
          return  cl.getFrameBounds();
        else
          return  new Rectangle(0,0,0,0);
    }
}

