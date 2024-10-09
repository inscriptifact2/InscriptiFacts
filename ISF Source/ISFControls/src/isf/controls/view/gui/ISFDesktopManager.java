package isf.controls.view.gui;

import isf.common.utils.LogManager;
import isf.controls.view.containers.JIFrame;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.beans.PropertyVetoException;
import javax.swing.DefaultDesktopManager;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: kamesh
 * Date: Jun 12, 2004
 * Time: 9:59:02 PM
 * To change this template use Options | File Templates.
 */
public class ISFDesktopManager extends DefaultDesktopManager {
    public static boolean isDragging = false;
    public static int frmWidth=1280,frmHeight=1280;
    public static final int FRMDIM = 1280;
    private static int noPTMS = 0;
    Logger log = LogManager.getLogger(isf.controls.view.gui.ISFDesktopManager.class);
    public static void addPTMs()
    {
        noPTMS++;
    }
    public static void closePTMs()
    {
        noPTMS--;
    }
    public static void resetPTMs()
    {
        noPTMS = 0;
    }
  
    public void beginDraggingFrame(JComponent jcomponent) {
        isDragging = true;
        super.beginDraggingFrame(jcomponent);
    }

    public void endDraggingFrame(JComponent jcomponent) {
        super.endDraggingFrame(jcomponent);
        isDragging = false;
    }

    public void iconifyFrame(JInternalFrame f) {
        // super.iconifyFrame(f);
        f.setVisible(false);
        try {
            f.setIcon(true);
        } catch (PropertyVetoException e) {
            log.error(e);  //To change body of catch statement use Options | File Templates.
        }

    }
    
    public void maximizeFrame(JInternalFrame f)
    {
       JIFrame frame = (JIFrame)f;
       if(!frame.getImagePanel().isPTMPanel() || noPTMS <2)
       {
          super.maximizeFrame(f);
          return;
       }
       boolean maxSize = false;
       if(frame.getImagePanel().mustCurbResize())
       {
        Rectangle r = f.getParent().getBounds();
         frmWidth = ((int)r.getWidth()>FRMDIM)?FRMDIM:(int)r.getWidth();
         frmHeight = ((int)r.getHeight()>FRMDIM)?FRMDIM:(int)r.getHeight();
        if(r.width >frmWidth || r.height >frmHeight){
      
        f.setMaximumSize(new Dimension(frmWidth,frmHeight));
        f.setBounds(0,0,frmWidth,frmHeight);
        try{
        f.setMaximum(true);
        }catch(Exception exp){log.error(exp);}
        
        }else
            maxSize = true;
       }else
           maxSize = true;
       if(maxSize)
           super.maximizeFrame(f);
    }
    
    public void resizeFrame(JComponent f,
                        int newX,
                        int newY,
                        int newWidth,
                        int newHeight)
    {
        JIFrame frame = (JIFrame)f;
       
       if(frame.getImagePanel().mustCurbResize() && noPTMS >=2)
       {
         Rectangle r = f.getParent().getBounds();
         frmWidth = ((int)r.getWidth()>FRMDIM)?FRMDIM:(int)r.getWidth();
         frmHeight = ((int)r.getHeight()>FRMDIM)?FRMDIM:(int)r.getHeight();
         
     
         if( newWidth > frmWidth)
             newWidth = frmWidth;
         
         if( newHeight > frmHeight)
             newHeight = frmHeight;
        }
         super.resizeFrame(f, newX, newY, newWidth, newHeight);
    }
}
