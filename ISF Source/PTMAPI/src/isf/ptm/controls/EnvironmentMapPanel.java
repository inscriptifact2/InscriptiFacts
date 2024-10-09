/*
 * EnvironmentMapFrame.java
 *
 * Created on October 2, 2004, 3:13 PM
 */
package isf.ptm.controls;

import isf.ptm.graphics.EnvironmentMap;
import isf.ptm.utils.Utils;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * The panel containing all the environment mapping user controls
 *
 * @author Default
 */
public class EnvironmentMapPanel extends JPanel implements MouseMotionListener,
        MouseListener {

    private EnvironmentMap environmentMap = null;
    private int[] displayBuffer = null;
    private int[] savedBuffer = null;
    private EnvironmentMapCanvas emc = new EnvironmentMapCanvas();
    private BufferedImage displayImage = null;
    private int startX = -1;
    private int startY = -1;
    private double[] cos = new double[360];
    private double[] sin = new double[360];
    private PTMWindow ptmw = null;

    {
        emc.setBorder(BorderFactory.createEtchedBorder());
        for (int i = 0; i < cos.length; i++) {
            cos[i] = Math.cos(Math.toRadians(i));
            sin[i] = Math.sin(Math.toRadians(i));
        }
    }

    /**
     *
     * @return
     */
    public EnvironmentMapCanvas getCanvas() {
        return emc;
    }

    public void setPTMWindow(PTMWindow p) {
        ptmw = p;
        ptmw.setEnvironmentMap(this.environmentMap);
    }

    public EnvironmentMapPanel() {
        _initEMP();
    }

    private void _initDraw() {
        environmentMap.refresh();
        displayImage = environmentMap.getImage();
        savedBuffer = Utils.grabPixels(displayImage);
        displayBuffer = new int[savedBuffer.length];
        for (int i = 0; i < savedBuffer.length; i++) {
            displayBuffer[i] = savedBuffer[i];
        }
    }

    private void _initEMP() {
        environmentMap = null;
        ptmw = null;
        emc.setPreferredSize(new Dimension(150, 150));
        this.add(emc);
    }

    public void unsetEnvironmentMap() {
    }

    public EnvironmentMap getEnvironmentMap() {
        return environmentMap;
    }

    public void redraw() {
        _initDraw();
    }

    public void setEnvironmentMap(EnvironmentMap em) {
        _setEM(em);
    }

    private void _setEM(EnvironmentMap em) {
        if (environmentMap != null) {
            environmentMap.release();
            environmentMap = null;
            System.gc();
        }
        environmentMap = em;
        environmentMap.refresh();
        displayImage = em.getImage();
        savedBuffer = Utils.grabPixels(displayImage);
        displayBuffer = new int[savedBuffer.length];
        for (int i = 0; i < savedBuffer.length; i++) {
            displayBuffer[i] = savedBuffer[i];
        }
        emc.setPreferredSize(new Dimension(150, 150));
        this.add(emc);
        addMouseListener(this);
        addMouseMotionListener(this);
        if (ptmw != null) {
            ptmw.setEnvironmentMap(environmentMap);
        }
    }

    /**
     * Creates a new instance of EnvironmentMapFrame
     *
     * @param em The environment map object
     * @param p The PTM Window
     */
    public EnvironmentMapPanel(EnvironmentMap em, PTMWindow p) {
        init(em, p);
    }

    private void init(EnvironmentMap em, PTMWindow p) {
        environmentMap = em;
        ptmw = p;
        em.refresh();
        displayImage = em.getImage();
        savedBuffer = Utils.grabPixels(displayImage);
        displayBuffer = new int[savedBuffer.length];
        for (int i = 0; i < savedBuffer.length; i++) {
            displayBuffer[i] = savedBuffer[i];
        }
        emc.setPreferredSize(new Dimension(150, 150));
        this.add(emc);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    private void _mDrag(MouseEvent e) {
        int x = xlateX(e.getX());
        int y = xlateY(e.getY());
        double lenV2 = Math.sqrt(startX * startX + startY * startY);
        double lenV1 = Math.sqrt(x * x + y * y);
        double Nx = ((double) x) / lenV1;
        double Ny = ((double) y) / lenV1;
        double Nsx = ((double) startX) / lenV2;
        double Nsy = ((double) startY) / lenV2;
        double cross = (Nx * Nsy - Ny * Nsx);
        double dot = (Nx * Nsx + Ny * Nsy);
        double a = Math.acos(dot);
        int aa = (int) Math.round(Math.toDegrees(a));
        int dx = x - startX;
        int dy = y - startY;

        if (cross < 0) {
            this.environmentMap.setAngle(aa);
            this.rotate(aa);
        } else {
            this.environmentMap.setAngle(359 - aa);
            this.rotate(359 - aa);
        }
        this.environmentMap.rotatedMap();
    }



    public void mouseDragged(MouseEvent e) {
        _mDrag(e);
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        startX = xlateX(e.getX());
        startY = xlateY(e.getY());
    }

    public void mouseReleased(MouseEvent e) {
    }

    private void _rotateTheta(int theta) {
        int width = displayImage.getWidth();
        int height = displayImage.getHeight();
        int radius = Math.min(width / 2, height / 2);
        displayBuffer = new int[savedBuffer.length];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int offset = j * width + i;
                int x = i - width / 2;
                int y = j - height / 2;
                double d = Math.sqrt(x * x + y * y);
                if (d > radius) {
                    displayBuffer[offset] = savedBuffer[offset];
                } else {
                    int rx = (int) Math.round(x * cos[theta] + y * sin[theta]);
                    int ry = (int) Math.round(y * cos[theta] - x * sin[theta]);
                    rx += width / 2;
                    ry += height / 2;
                    if (rx < 0) {
                        rx = 0;
                    }
                    if (ry < 0) {
                        ry = 0;
                    }
                    if (rx > width - 1) {
                        rx = width - 1;
                    }
                    if (ry > width - 1) {
                        ry = height - 1;
                    }
                    displayBuffer[offset] = savedBuffer[ry * width + rx];
                }
            }
        }
        emc.repaint();
        if (ptmw != null) {
            ptmw.fireTransform();
        }
    }

    class EnvironmentMapCanvas extends JComponent {

        public void paintComponent(Graphics g) {
            _pc(g);
        }

        private void _pc(Graphics g) {
            Rectangle r = this.getBounds();
            Graphics2D g2d = (Graphics2D) g;
            if (displayBuffer != null && environmentMap != null) {
                g.drawImage(Utils.createBufferedImage(displayBuffer,
                        environmentMap.getImage().getWidth()), 0, 0, r.width,
                        r.height, this);
            } else {
                g.setColor(Color.white);
                g.fillRect(0, 0, r.width, r.height);
            }
        }
    }

    private int xlateX(int x) {
        return x - this.getWidth() / 2;
    }

    private int xlateY(int y) {
        return y - this.getHeight() / 2;
    }

    public void rotate(int theta) {
        _rotateTheta(theta);
    }
}
