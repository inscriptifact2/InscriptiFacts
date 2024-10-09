package isf.controls.view.containers;

import isf.controls.controllers.LoadManager;
import isf.controls.controllers.PTMFrameManager;
import isf.controls.controllers.ToolbarManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class SidPanel extends ImagePanel implements MouseMotionListener {

    public ImageIcon ic;
    private int width;
    private int height;
    private int maxlevel;
    private int curlevel;
    private String imgName,  fileSize;
    private boolean firstTime = true;

    public SidPanel() {
        super();
        this.setBackground(new Color(0, 0, 0));
        ep = new Point(0, 0);
        sp = new Point(0, 0);
        addMouseMotionListener(this);
        working = false;
    // addKeyListener(this);
    // requestDefaultFocus();

    }

    public void addImage(String fileName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addImage(ImageIcon ing) {
        ic = ing;
        ep = new Point(0, 0);
        sp = new Point(0, 0);
        if (firstTime) {
            //mode = 0;
            if (ing != null) {
                LoadManager.getInstance().updateCount();
                LoadManager.getInstance().update(250 * 1024);
            }
            firstTime = false;
        }

        repaint();
        ToolbarManager.getInstance().updateToolbar();

    }

    public void paint(Graphics g) {
         int mode = PTMFrameManager.getMode();
       // updateSizeInformation();

        if (ic == null) {
            return;
        }
        Rectangle r;

        r = getBounds();
        // System.out.println("called from paint "+this + " bounds:"+r);

        if (aw != r.width || ah != r.height) {
            ax = ic.getIconWidth();
            ay = ic.getIconHeight();
            aw = r.width;
            ah = r.height;
            offImg = null;
            offScreen = null;
            offImg = (BufferedImage) createImage(r.width, r.height);
            offScreen = offImg.getGraphics();
        } else if (offScreen == null) {
            offImg = null;
            offScreen = null;
            offImg = (BufferedImage) createImage(r.width, r.height);
            offScreen = offImg.getGraphics();
        }
        if (mode != 4) {
            offScreen.setColor(Color.black);
            offScreen.fillRect(0, 0, r.width, r.height);
            offScreen.drawImage(ic.getImage(), Math.abs((ax - r.width) / 2), Math.abs((ay - r.height) / 2), this);
            if ((sp.x != 0 && sp.y != 0 && ep.x != 0 && ep.y != 0) && mode == 3) {
                drawRect(offScreen);
            }
        } else if (mode == 4) {
            offScreen.drawImage(ic.getImage(), (Math.abs((ax - r.width) / 2) - (sp.x - ep.x)), (Math.abs((ay - r.height) / 2) - (sp.y - ep.y)), this);

        }


        g.drawImage(offImg, 0, 0, this);
        r = null;

    }

    public void mouseMoved(MouseEvent me) {
    }

    public void mouseDragged(MouseEvent me) {
         int mode = PTMFrameManager.getMode();
        if (active && (mode == 3 || mode == 4)) {
            ep = new Point(me.getX(), me.getY());
            repaint();
        }
    }

    /*  public static void main(String args[]) {
      JFrame jframe = new JFrame();
        jframe.setSize(400, 400);
        ViewerPanel imagepanel = new ViewerPanel("SID"); ///browse   "&lev=" + curLev + "&wid=" + w + "&hei=" + h + "&cp=" + cpx + "," + cpy;
          imagepanel.getPanel().addImage(new ImageIcon("http://lannea.usc.edu/lizardtech/iserv/getimage?cat=SidV4&item=/Fort_0000-111_LE1_ir_pf.sid&bg=000000&lev=5&wid=370&hei=370&cp=0.5,0.5"));
        Container container = jframe.getContentPane();
        container.add(imagepanel);
        jframe.setVisible(true);
        //imagepanel.getPanel().addImage(new ImageIcon("images/geotest.jpg"));
      //  imagepanel.getPanel().setStart(new Point(10, 10), 3);
    }*/

    public boolean isAtMaxLevel() {
        return curlevel <= 0;
    }

    public boolean isLoading() {
        return false;
    }

    public String getDetails() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<html><body><table border=1><tr><td><font face=verdana size=3 color=black><b>File Name :</b></font></td><td colspan=3><font face=verdana size=3 color=midnightblue><b>" + imgName);
        buffer.append("</b></font></td></tr>");
        buffer.append("<tr><td><font face=verdana size=3 color=black><b>File Size :</b></font></td><td colspan=3><font face=verdana size=3 color=midnightblue><b>");
        buffer.append(fileSize + " bytes</b></font></td></tr>");
        buffer.append("<tr><td><font face=verdana size=3 color=black><b>Image Width :</b></font></td><td align = center><font face=verdana size=3 color=midnightblue><b>");
        buffer.append(width + " pixels</b></font></td><td><font face=verdana size=3 color=black><b>Image Height :</b></font></td><td align= center><font face=verdana size=3 color=midnightblue><b>" + height + " pixels</b></font></td></tr>");
        buffer.append("<tr><td><font face=verdana size=3 color=black><b>Max. Levels :</b></font></td><td align = center><font face=verdana size=3 color=midnightblue><b>");
        buffer.append(maxlevel + "</b></font></td><td><font face=verdana size=3 color=black><b>Current Level :</b></font></td><td align=center><font face=verdana size=3 color=midnightblue><b>" + curlevel + "</b></font></td></tr></table></body></html>");


        return buffer.toString();
    }

    public int getImgWidth() {
        return width;
    }

    public void setImgWidth(int width) {
        this.width = width;
    }

    public int getImgHeight() {
        return height;
    }

    public void setImgHeight(int height) {
        this.height = height;
    }

    public int getMaxLevel() {
        return maxlevel;
    }

    public void setMaxLevel(int maxlevel) {
        this.maxlevel = maxlevel;
    }

    public int getCurLevel() {
        return curlevel;
    }

    public void setCurLevel(int curlevel) {
        this.curlevel = curlevel;
        updateSizeInformation();
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }
    
    private void updateSizeInformation()
    {
        String msg = "Zoom Level is at:" + curlevel;
        if(curlevel < 0 && notifier !=null)
        {
                msg = msg + ". Image is interpolated at this level.";
        }
        if(curlevel>0)
            shown = false;
         notifier.updateSize(msg);
       
            
    }
    boolean shown = false;
   public  void setMessageShown(boolean b)
    {
        shown = b;
    }
    public  boolean getMessageShown(){return shown;};

    
    public boolean mustCurbResize() {
        return false;
    }
}
