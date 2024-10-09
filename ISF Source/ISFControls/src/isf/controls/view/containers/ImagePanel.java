/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package isf.controls.view.containers;


//import com.sun.image.codec.jpeg.JPEGCodec;
import isf.common.utils.LogManager;
import isf.controls.view.listeners.SizeChangeListener;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.util.GregorianCalendar;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import org.apache.log4j.Logger;

/**
 * @author IN030223
 */
public abstract class ImagePanel extends JPanel {

    protected Graphics offScreen;
    protected BufferedImage offImg;
    protected Point sp, ep;
   // protected int mode = 0;
    protected int ax, ay, aw, ah;
    
    protected boolean active=false;
    protected boolean working = false;

    public abstract void addImage(ImageIcon ing);

    public abstract void addImage(String fileName);
    Logger log = LogManager.getLogger(isf.controls.view.containers.ImagePanel.class);

    public abstract boolean isLoading();

    public boolean isPTMPanel() {
        return false;
    }
    
     public boolean isHSHPanel() {
        return false;
    }
    
     public abstract boolean mustCurbResize(); 


    public boolean isAtMaxLevel() {
        return false;
    }
    
    public abstract void setMessageShown(boolean b);
    public abstract boolean getMessageShown();

  

    public void setStart(Point st) {
       
        sp = st;
       
    }
    public void setEnd(Point ed) {
       
        ep = ed;
       
    }

    public void drawRect(Graphics g) {
        g.setColor(Color.cyan);
        int x = Math.min(sp.x, ep.x);
        int y = Math.min(sp.y, ep.y);
        int w = Math.max(sp.x, ep.x) - x;
        int h = Math.max(sp.y, ep.y) - y;
        Rectangle selectRect = new Rectangle(x, y, w, h);
        //System.out.println("Paint");

        g.drawRect(selectRect.x, selectRect.y, selectRect.width, selectRect.height);
        g.drawRect(selectRect.x + 1, selectRect.y + 1, selectRect.width - 2, selectRect.height - 2);
        g.setPaintMode();
    }

    public abstract String getDetails();

    public String saveImage(String name, String type) {
        String fname = name.substring(0, name.indexOf(".") + 1);
        fname = (new StringBuilder()).append(fname).append(type).toString();
        //fname = fname + type;
        String msg = fname;
        try {
            int w = offImg.getWidth();
            int h = offImg.getHeight();
            int tp = BufferedImage.TYPE_INT_RGB;  // see api for options
            BufferedImage bi = new BufferedImage(w + 4, h + 54, tp);
            Graphics g = bi.getGraphics();
            g.setColor(Color.white);
            g.drawRect(0, 0, w + 4, h + 54);
            g.drawImage(offImg, 2, 2, w, h, this);
            g.setColor(Color.white);
            g.drawLine(2, h + 2, w + 2, h + 2);
            g.drawString("© Copyright 2001 - "+(new GregorianCalendar()).get(GregorianCalendar.YEAR)+" Univ. of Southern California.", 10, h + 20);
            FileOutputStream fos = new FileOutputStream(fname);
            ImageIO.write(bi,"jpeg",fos);
            fos.close();
             msg = (new StringBuilder()).append("Image has been saved as ").append(fname).toString();
        } catch (Exception ie) {
            log.error(ie);
            msg="error";
        }finally{
            return msg;
        }
    }
     public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
   protected SizeChangeListener notifier;

    public void setSizeNotifier(SizeChangeListener scl) {
        this.notifier = scl;
    }
}
