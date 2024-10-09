package isf.controls.utils;

import isf.controls.view.containers.ISFGlassPane;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JRootPane;

/**
 * Created by IntelliJ IDEA.
 * User: kamesh
 * Date: Jan 18, 2004
 * Time: 6:57:19 AM
 * To change this template use Options | File Templates.
 */
public final class GlassPaneManager {

    private ISFGlassPane jpanel;
    private JRootPane jrp;
     private JFrame mainApp;
    private static GlassPaneManager gpm;

    private GlassPaneManager() {

    }

    public static GlassPaneManager getInstance() {
        if (gpm == null) {
            gpm = new GlassPaneManager();
        }
        return gpm;
    }

    public void showComponent(JComponent c, Rectangle b) {
        jpanel.removeAll();
        jpanel.add(c);
        
         if(c.getClass().getName().equals( "isf.view.ControlsPopUp") && bounds != null)
         {
            Dimension d = c.getPreferredSize();
            bounds = new Rectangle(bounds.x,bounds.y,d.width,d.height);
            c.setBounds(bounds);
         } else
            c.setBounds(b);
        //c.setBounds(b);
        jpanel.revalidate();
        jpanel.validate();
        jpanel.setVisible(true);

    }
    
    public void updateControlBounds(Rectangle r)
    {
        bounds = r;
    }
    Rectangle bounds  ;

    public void showComponent(JComponent c) {
        jpanel.removeAll();
        jpanel.add(c);
        if(c.getClass().getName().equals( "isf.view.ControlsPopUp") && bounds != null)
        {
            
            Dimension d = c.getPreferredSize();
            bounds = new Rectangle(bounds.x,bounds.y,d.width,d.height);
            c.setBounds(bounds);
        
        }else
            c.setBounds(c.getBounds());
        jpanel.revalidate();
        jpanel.validate();
        jpanel.repaint();
        jpanel.setVisible(true);
        // System.out.println(jpanel.isShowing() );
        //    System.out.println( jrp.getLayeredPane().getComponentsInLayer(500).length );

        //   jrp.revalidate();
        //  jrp.validate();
        //.repaint();
    }

    public void hideAll() {
        jpanel.removeAll();
        jpanel.revalidate();
        jpanel.validate();
        jpanel.setVisible(false);
 

    }

    public void setUpGlasssPane(JFrame frame) {
        mainApp = frame;
        jrp = mainApp.getRootPane();
        jpanel = new ISFGlassPane(frame);
        jrp.setGlassPane(jpanel);
      
    }

    public void repaintRoot()
    {
        //mainApp.getContentPane().revalidate();
        mainApp.getContentPane().validate();
        mainApp.getContentPane().repaint();
    }

    public boolean isShowing() {
        return jpanel.isShowing();
    }

    public Graphics getGraphics() {
        return jpanel.getGraphics();
    }
    
    public Component getComponent()
    {
        return jpanel.getComponent(0);
    }

}
