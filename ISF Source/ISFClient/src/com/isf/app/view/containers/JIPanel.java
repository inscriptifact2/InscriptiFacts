package com.isf.app.view.containers;



import isf.controls.controllers.PTMFrameManager;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class JIPanel extends JPanel implements MouseMotionListener {
    public ImageIcon ic;
    public Graphics offScreen;
    public Image offImg;
    private Point sp, ep;
    int ax, ay, aw, ah;
    public JIPanel() {
        super();
        ep = new Point(0, 0);
        sp = new Point(0, 0);
       // addMouseMotionListener(this);
        // addKeyListener(this);
        // requestDefaultFocus();

    }

    public void addImage(ImageIcon ing) {
        ic = ing;
        ep = new Point(0, 0);
        sp = new Point(0, 0);
       // mode = 0;
        repaint();
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
            offImg = createImage(r.width, r.height);
            offScreen = offImg.getGraphics();
        } else if (offScreen == null) {
            offImg = null;
            offScreen = null;
            offImg = createImage(r.width, r.height);
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

   /* public void setStart(Point st, int m) {
        sp = st;
        mode = m;
    }*/


    public void mouseMoved(MouseEvent me) {
    }

    public void mouseDragged(MouseEvent me) {
        int mode = PTMFrameManager.getMode();
        if (mode == 3 || mode == 4) {
            ep = new Point(me.getX(), me.getY());
            repaint();
        }
    }


    public void keyTyped(KeyEvent event) {
        //System.out.println("key pressed:"+event.getKeyCode() +"--"+event.isShiftDown());
    }

    public void keyPressed(KeyEvent event) {
    }

    public void keyReleased(KeyEvent event) {
    }

    public void setMode(int mod) {
      //  System.out.println("spinjip:" + sp);
      //  mode = mod;
    }
}
