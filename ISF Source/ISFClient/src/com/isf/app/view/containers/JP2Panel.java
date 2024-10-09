package com.isf.app.view.containers;

//import isf.controls.utils.LogManager;
//import org.apache.log4j.Logger;

import isf.controls.controllers.PTMFrameManager;
import isf.controls.controllers.ToolbarManager;
import isf.controls.view.containers.ImagePanel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;


public class JP2Panel extends ImagePanel implements MouseMotionListener {
    public ImageIcon ic;


    private int width;
    private int height;
    private int maxlevel;
    private int curlevel;
    private String imgName, fileSize;

   

    public JP2Panel() {
        super();
        ep = new Point(0, 0);
        sp = new Point(0, 0);
        addMouseMotionListener(this);
 

    }

    public void addImage(String fileName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addImage(ImageIcon ing) {
        ic = ing;
        ep = new Point(0, 0);
        sp = new Point(0, 0);
      //  mode = 0;
        repaint();
        ToolbarManager.getInstance().updateToolbar();
    }

    public void paint(Graphics g) {
        int mode = PTMFrameManager.getMode();

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
            if ((sp != null && ep != null) && mode == 3)
                drawRect(offScreen);

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
        if (mode == 3 || mode == 4) {
            ep = new Point(me.getX(), me.getY());
            repaint();
        }
    }



    public boolean isAtMaxLevel() {
        return curlevel == 0;
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
    public  void setMessageShown(boolean b){};
    public  boolean getMessageShown(){return false;};

   
    public boolean mustCurbResize() {
        return false;
    }
}
