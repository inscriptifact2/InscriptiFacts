/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.viewer.controls;

import isf.common.utils.CacheManagerConstants;
import isf.common.utils.LogManager;
import isf.controls.controllers.PTMFrameManager;
import isf.controls.view.containers.ControlMenu;
import isf.controls.view.containers.PTMPanel;
import isf.ptm.utils.LightSource;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import org.apache.log4j.Logger;

/**
 *
 * @author IN030223
 */
public class ThumbnailControlMenu extends ControlMenu {

    PTMPanel pf;
    ImagePane imgPanel = new ImagePane();
    Logger log = LogManager.getLogger(isf.viewer.controls.ThumbnailControlMenu.class);

    public void init() {
        setBackground(new Color(0xd9d9d9));
        setForeground(new Color(0xAC5539));
        pf = (PTMPanel) (PTMFrameManager.getInstance().getPTMWindow());
        setLayout(new BorderLayout());
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        p.setBackground(new Color(0xd9d9d9));
        p.setForeground(new Color(0xAC5539));

        if (pf.getPTM() != null) {
            String fName = pf.getPTM().getName();
            fName = fName.substring(fName.lastIndexOf(File.separatorChar) + 1);
            if (fName.indexOf(".") > -1) {
                fName = fName.substring(0, fName.indexOf("."));
            }
            String thumbname = fName + "thumb.jpg";

            /* if (((PTMPanelImpl) pf).copied) {
            imgPanel.setIcon(pf.getPTM().getName() + "copy_thumb.jpg");
            } else {
            imgPanel.setIcon(pf.getPTM().getName() + "thumb.jpg");
            }*/
            imgPanel.setIcon(thumbname);
            imgPanel.setDisplay(pf.getDisplayRect(), pf.getCurrentSize());
            p.add(imgPanel);
        }
        add(p, BorderLayout.CENTER);
        JLabel msg = new JLabel();
        if (pf.isAdditionalLight()) {

            msg.setText("<html><body>To move designated light, grab and drag dot " +
                    "(green or red) on image or thumbnail.</body></html>");


        } else {
            msg.setText("<html><body>Move cursor in the selected image or in the thumbnail " +
                    "above to move the light around in the image.</body></html>");

        }
        add(msg, BorderLayout.SOUTH);
        TitledBorder b = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Thumbnail");
        b.setTitleJustification(TitledBorder.LEFT);
        this.setBorder(b);
        PTMFrameManager.getInstance().registerChangeListener(this, pf);
    }

    public Component getComponent() {
        return this;
    }

    public MenuElement[] getSubElements() {
        return new MenuElement[0];
    }

    public void menuSelectionChanged(boolean arg0) {
    }

    public void processKeyEvent(KeyEvent arg0, MenuElement[] arg1, MenuSelectionManager arg2) {
    }

    public void processMouseEvent(MouseEvent arg0, MenuElement[] arg1, MenuSelectionManager arg2) {
    }

    public Dimension getPreferredSize() {
        return new Dimension(265, 305);
    }

    public Dimension getMinimumSize() {
        return new Dimension(260, 300);
    }

    public Dimension getMaximumSize() {
        return new Dimension(270, 310);
    }

    public void resetControls() {
        pf = (PTMPanel) (PTMFrameManager.getInstance().getPTMWindow());
        if (pf != null && pf.getPTM() != null) {
            String fName = pf.getPTM().getName();
            fName = fName.substring(fName.lastIndexOf(File.separatorChar) + 1);
            if (fName.indexOf(".") > -1) {
                fName = fName.substring(0, fName.indexOf("."));
            }
            String thumbname = fName + "thumb.jpg";

            imgPanel.setIcon(thumbname);
            // imgPanel.setIcon(pf.getPTM().getName() + "thumb.jpg");
            imgPanel.setDisplay(pf.getDisplayRect(), pf.getCurrentSize());
        }


    }

    class ImagePane extends JPanel implements MouseMotionListener, MouseListener {

        String path = CacheManagerConstants.cacheFolderName ;
        String file = "";
        // Rectangle rect = new Rectangle(0,0,0,0);
        // Dimension dim = new Dimension(0,0);
        BufferedImage image = null;
        Rectangle viewRect;
        float xF, yF;
        int posX, posY;
        int curWid, curHei;

        public ImagePane() {
            addMouseListener(this);
            addMouseMotionListener(this);
            setBackground(Color.BLUE);
        }

        public void setIcon(String img) {
            file = img;

            try {
                image = ImageIO.read(new File(path + File.separator + file));
            } catch (Exception exp) {
               log.error( exp);
            }

        }

        public void setDisplay(Rectangle r, Dimension view) {

            int vX = 0, vY = 0;
            if (image != null) {
                int iw = image.getWidth();
                int ih = image.getHeight();

                xF = (float) iw / (float) view.getWidth();
                yF = (float) ih / (float) view.getHeight();
                posX = ((int) (r.x * xF));
                posY = ((int) (r.y * yF));
                if (posX < 0) {
                    vX = 0 - posX;
                }
                if (posY < 0) {
                    vY = 0 - posY;
                }

                int wid = (r.width * xF > iw) ? iw : (int) (r.width * xF);
                int hei = (r.height * yF > ih) ? ih : (int) (r.height * yF);
                // int wid = (int) (r.width * xF);
                // int hei = (int) (r.height * yF);

                viewRect = new Rectangle(vX, vY, wid, hei);
            }
            curWid = (int) view.getWidth();
            curHei = (int) view.getHeight();
            validate();
            revalidate();
            repaint();
        }

        public void paint(Graphics g) {

            if (file != null) {
                Rectangle r = getBounds();
                g.setColor(Color.black);
                g.fillRect(0, 0, r.width + r.x, r.height + r.y);

                g.drawImage(image, 0, 0, this);
                if (viewRect != null) {
                    g.setColor(Color.cyan);
                    g.drawRect(viewRect.x, viewRect.y, viewRect.width, viewRect.height);
                }
                if (pf.isAdditionalLight()) {
                    LightSource[] lights = pf.getLights();
                    g.setColor(Color.green);
                    g.fillArc((int) ((lights[0].getX()) * xF) - vals[0], (int) ((lights[0].getY()) * yF) - vals[0], vals[0] * 2, vals[0] * 2, 0, 360);
                    g.setColor(Color.red);
                    g.fillArc((int) ((lights[1].getX()) * xF) - vals[1], (int) ((lights[1].getY()) * yF) - vals[1], vals[1] * 2, vals[1] * 2, 0, 360);

                }
            }

        }

        public Dimension getPreferredSize() {
            if (image != null) {
                return new Dimension(image.getWidth(), image.getHeight());
            } else {
                return new Dimension(265, 305);
            }
        }

        public Dimension getMinimumSize() {
            if (image != null) {
                return new Dimension(image.getWidth(), image.getHeight());
            } else {
                return new Dimension(260, 300);
            }
        }

        public Dimension getMaximumSize() {
            if (image != null) {
                return new Dimension(image.getWidth(), image.getHeight());
            } else {
                return new Dimension(270, 310);
            }
        }

        public void mouseDragged(MouseEvent e) {
            dragging = true;
            if (dragging) //&& pf.isAdditionalLight()
            {
                int x = (int) ((e.getX()) / xF);
                int y = (int) ((e.getY()) / yF);
                x = (x >= curWid) ? curWid : (x < 0) ? 0 : x;
                y = (y >= curHei) ? curHei : (y < 0) ? 0 : y;
                pf.updateLightPosition(x, y);
                repaint();
            }
        }
        int[] vals = new int[]{2, 2};

        public void mouseMoved(MouseEvent e) {
            if (pf.isAdditionalLight()) {
                int x = e.getX();
                int y = e.getY();

                int px, py;
                LightSource s;
                LightSource[] lights = pf.getLights();

                for (int p = 0; p < lights.length; p++) {
                    s = lights[p];
                    px = (int) (s.getX() * xF);
                    py = (int) (s.getY() * yF);
                    if (((px - x) * (px - x)) + ((py - y) * (py - y)) <= 16) {
                        vals[p] = 4;
                    } else {
                        vals[p] = 2;
                    }
                }
                repaint();
            }
        }

        public void mouseClicked(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            int px, py;
            LightSource s;
            LightSource[] lights = pf.getLights();

            for (int p = 0; p < lights.length; p++) {
                s = lights[p];
                px = (int) (s.getX() * xF);
                py = (int) (s.getY() * yF);
                if (((px - x) * (px - x)) + ((py - y) * (py - y)) <= 16) {
                    pf.setActiveLight(p);
                    PTMFrameManager.getInstance().refreshControls();
                    break;
                }
            }
        }
        boolean dragging = false;

        public void mouseReleased(MouseEvent e) {

            if (dragging) //&& pf.isAdditionalLight()
            {
                int x = (int) (e.getX() / xF);
                int y = (int) (e.getY() / yF);
                dragging = false;
                x = (x >= curWid) ? curWid : (x < 0) ? 0 : x;
                y = (y >= curHei) ? curHei : (y < 0) ? 0 : y;
                pf.updateLightPosition(x, y);
                repaint();
            }

        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }
    }
}
