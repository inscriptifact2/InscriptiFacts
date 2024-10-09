/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package isf.controls.view.containers;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * @author IN030223
 */
public class ISFGlassPane extends JPanel implements AWTEventListener  { //,MouseMotionListener{

    private final JFrame frame;
    private Point point  = new Point();
    private boolean processEvent=true;

    public ISFGlassPane(JFrame frame) {
        super(null);
        this.frame = frame;
        setOpaque(false);
        setLayout(null);
        //  addMouseListener(this);
        //  addMouseMotionListener(this);

    }

    public void setPoint(Point point)
    {
        this.point = point;
    }
    public void eventDispatched(AWTEvent event) {
        if(event instanceof MouseEvent )
        {
            MouseEvent me = (MouseEvent) event;

            if(contains(me.getX(),me.getY()) )
              return;
            if(!SwingUtilities.isDescendingFrom(me.getComponent(),frame)){
                return;
            }
            if(me.getID() == MouseEvent.MOUSE_EXITED && me.getComponent()==frame)
            {
                point = null;
            }  else
            {
                MouseEvent converted = SwingUtilities.convertMouseEvent(me.getComponent(),me,frame.getGlassPane());
                point = converted.getPoint();
            }
        }
    }

    public boolean contains(int x, int y)
    {
       for(int i=0;i<getComponents().length ;i++)
        {
            Component c = getComponent(i);
            if( c.getBounds().contains(x,y)  )
                return true;
        }
        return false;
       // return true;
    }


}
