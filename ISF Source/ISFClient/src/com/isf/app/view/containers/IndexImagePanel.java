package com.isf.app.view.containers;

import com.isf.app.view.listeners.ClickListener;
import isf.common.utils.LogManager;
import isf.controls.utils.CursorManager;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import org.apache.log4j.Logger;

public class IndexImagePanel extends JPanel
        implements MouseListener, MouseMotionListener {

    private double imageScale;
    private Graphics m_MemG, memG;
    private ImageIcon tempImage;
    private Image offImg, memImg;
    private Dimension displayDim;
    private Dimension imageDim;
    private Insets ins;
    private Point start;
    private Point end;
    private int m_nMouseMode;
    private Rectangle selectRect;
    private Rectangle imageRect;
    private ImageIcon icon;
    private boolean paint;
    Rectangle actualRect;
    private static Cursor drsel;
    int ax, ay, aw, ah;
    private ClickListener cl;
    private Rectangle r;
Logger logger = LogManager.getLogger(com.isf.app.view.containers.IndexImagePanel.class);

    public void reset() {
        tempImage = null;
        offImg = null;
        memImg = null;
        imageScale = 1.0D;
        start = new Point(0, 0);
        end = new Point(0, 0);
        selectRect = new Rectangle(0, 0, 0, 0);
        actualRect = new Rectangle(0, 0, 0, 0);
        repaint();
    }


    public void DrawSelectionRectangle() {
        updateOffscr();
        if (m_MemG != null) {
            if (m_nMouseMode == 0) {
                m_MemG.setColor(Color.cyan);

            } else if (m_nMouseMode == 1) {
                m_MemG.setColor(Color.yellow);

            }
            logger.debug("memg is not null:");
            m_MemG.drawRect(selectRect.x, selectRect.y, selectRect.width, selectRect.height);
            m_MemG.drawRect(selectRect.x + 1, selectRect.y + 1, selectRect.width - 2, selectRect.height - 2);

            m_MemG.setPaintMode();
            //m_MemG.dispose();   //commented out for fixing issue of not seeing yellow dragline in jdk1.4.2 and above
            Graphics g = getGraphics();
            g.drawImage(offImg, ins.left, ins.top, this);
            g.dispose();
        }

    }

    public void clearAll() {
        start = new Point(0, 0);
        end = new Point(0, 0);
        selectRect = new Rectangle(0, 0, 0, 0);
        actualRect = new Rectangle(0, 0, 0, 0);
    }

    public IndexImagePanel() {
        m_nMouseMode = 0;
        paint = false;
        actualRect = new Rectangle(0, 0, 0, 0);
        setPreferredSize(new Dimension(500, 400));
        setBackground(Color.black);
        setOpaque(true);
        ins = new Insets(0, 0, 0, 0);
        imageScale = 1.0D;
        tempImage = null;
        displayDim = new Dimension(0, 0);
        imageDim = new Dimension(0, 0);
        //panelDim = new Dimension(0, 0);
        start = new Point(0, 0);
        end = new Point(0, 0);
        imageRect = new Rectangle();
        selectRect = new Rectangle();
        addMouseListener(this);
        addMouseMotionListener(this);
        offImg = null;
        memImg = null;
        m_MemG = null;
        memG = null;
        icon = null;
    }

    public void DisplayMessage(String s) {
        // Debug.debug(s);
    }

    public void addClickListener(ClickListener cl) {
        this.cl = cl;
    }

    public void setImage(ImageIcon imageicon) {
        paint = true;
        if (imageicon == null) {
            icon = imageicon;
            tempImage = null;
        } else {
            imageDim = new Dimension(imageicon.getIconWidth(), imageicon.getIconHeight());
            tempImage = imageicon;
            // tempImage = null;
            offImg = null;
            memImg = null;
            m_MemG = null;
            memG = null;
            imageScale = 1.0D;
            start = new Point(0, 0);
            end = new Point(0, 0);
            selectRect = new Rectangle(0, 0, 0, 0);
            actualRect = new Rectangle(0, 0, 0, 0);
            setImageScale();
            // setBackground(Color.black);
        }
        repaint();
    }

    public void resetMem() {
        offImg = null;
        memG = null;
        memImg = null;
        memG = null;
        System.gc();
    }

    public void paint(Graphics g) {
        //	System.out.println("called PAINT:"+selectRect);
        if (tempImage == null) {
            super.paint(g);
            return;
        }
        if (r == null)
            r = getBounds();
        if ((aw != r.width && ah != r.height) || m_MemG == null || paint) {
            ax = tempImage.getIconWidth();
            ay = tempImage.getIconHeight();
            aw = r.width;
            ah = r.height;
            resetMem();
            offImg = createImage(r.width, r.height);
            memImg = createImage(r.width, r.height);
            m_MemG = offImg.getGraphics();
            memG = memImg.getGraphics();
            memG.setColor(Color.black);
            memG.fillRect(0, 0, aw, ah);
            memG.drawImage(tempImage.getImage(), ins.left, ins.top, displayDim.width, displayDim.height, this);

        }
        /*	else if(m_MemG == null || paint)
            {
                offImg = createImage(r.width, r.height);
                m_MemG = offImg.getGraphics();

            }*/
        m_MemG.setColor(Color.black);
        m_MemG.fillRect(0, 0, aw, ah);
        m_MemG.drawImage(tempImage.getImage(), ins.left, ins.top, displayDim.width, displayDim.height, this);
        //  m_MemG.dispose();   //commented out to fix issue in jdk1.4.2 and above
        g.drawImage(offImg, ins.left, ins.top, this);
        if (paint) {
            g.setColor(Color.cyan);
            g.drawRect(selectRect.x, selectRect.y, selectRect.width, selectRect.height);
            g.drawRect(selectRect.x + 1, selectRect.y + 1, selectRect.width - 2, selectRect.height - 2);
            g.setPaintMode();
        }
        g.dispose();
        //   m_MemG.dispose();         //added below for testing


    }

    public void updateOffscr() {
        //offImg = createImage(aw, ah);
        //m_MemG = offImg.getGraphics();
        if (memImg != null) {
            m_MemG.setColor(Color.black);
            m_MemG.fillRect(0, 0, aw, ah);
            m_MemG.drawImage(memImg, ins.left, ins.top, this);
            //commented out both here for fixing issue in jdk1.4.2 and above
            // memG.dispose();
            // m_MemG.dispose();
        }

    }

    public void mouseEntered(MouseEvent mouseevent) {
        if (!CursorManager.wait)
            CursorManager.changeCursor(drsel);
//        else
//           CursorManager.changeCursor(drsel);
    }

    public void mouseExited(MouseEvent mouseevent) {
        if (!CursorManager.wait)
            CursorManager.changeCursor(0);
    }

    public void mouseClicked(MouseEvent mouseevent) {
    }

    public void mouseMoved(MouseEvent mouseevent) {
    }

    public void mousePressed(MouseEvent mouseevent) {
        //updateOffscr();
        if (cl != null)
            cl.fireClose();
        start = new Point(mouseevent.getPoint());
        end = new Point(0, 0);
        selectRect = new Rectangle(0, 0, 0, 0);
        actualRect = new Rectangle(0, 0, 0, 0);
        m_nMouseMode = 1;
        paint = false;


    }

    public void mouseDragged(MouseEvent mouseevent) {

        //updateOffscr();
        if (m_nMouseMode == 1) {
            int i = mouseevent.getX();
            int j = mouseevent.getY();
            if (end.x == i && end.y == j) {
                return;
            }
            //DrawSelectionRectangle(getGraphics());

            end = new Point(i, j);
            int k = Math.min(start.x, end.x);
            int l = Math.max(start.x, end.x);
            int i1 = Math.min(start.y, end.y);
            int j1 = Math.max(start.y, end.y);
            int k1 = j1 - i1;
            int l1 = l - k;
            imageRect = selectRect;

            selectRect = new Rectangle(k, i1, l1, k1);

            DrawSelectionRectangle();
            // m_nMouseMode = 2;
        }
    }

    public void mouseReleased(MouseEvent mouseevent) {
        int i = 0;
        int j = 0;
        int k = 0;
        int l = 0;
        end = new Point(mouseevent.getX(), mouseevent.getY());
        if (selectRect.width > 0 && selectRect.height > 0) {
            l = (int) (imageScale * (double) (displayDim.height - selectRect.y) + 0.5D);
            k = (int) (imageScale * (double) selectRect.x + 0.5D);
            j = (int) (imageScale * (double) selectRect.width + 0.5D);
            i = (int) (imageScale * (double) selectRect.height + 0.5D);
        }
        //  Debug.debug(" selected points = [x=" + k + ", y=" + l + ", width=" + j + ", height=" + i + " ]");
        actualRect = new Rectangle(k, l, j, i);

        m_nMouseMode = 0;
        DrawSelectionRectangle();
        paint = true;
        Point cp = getLocationOnScreen();
        if (!start.equals(end))
            cl.popButton(new Point(cp.x + mouseevent.getX(), cp.y + mouseevent.getY()));
        //repaint();
    }

    public Rectangle getSelectedPoints() {
        // System.out.println(" in imagepanel " + actualRect);
        return actualRect;
    }

    private void setImageScale() {
        double d = 1.0D;
        double d2 = 1.0D;
        int i = 0;
        int j = 0;
        Dimension dimension = getSize();
        i = dimension.width - ins.left - ins.right;
        j = dimension.height - ins.top - ins.bottom;
        if (icon != null) {
            imageDim = new Dimension(icon.getIconWidth(), icon.getIconHeight());
        }
        if (j <= 0 || i <= 0 || imageDim.height <= 0 || imageDim.width <= 0) {
            displayDim = new Dimension(0, 0);
            imageScale = 1.0D;
        } else {
            double d1 = (double) i / (double) j;
            double d3 = (double) imageDim.width / (double) imageDim.height;
            if (d1 > d3) {
                displayDim = new Dimension((int) ((double) j * d3), j);
            } else {
                displayDim = new Dimension(i, (int) ((double) i / d3));
            }
            imageScale = 1000D / (double) displayDim.height;
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(1024, 1024);
    }


    static {
        Dimension d = Toolkit.getDefaultToolkit().getBestCursorSize(32, 32);


        if (d.height != 0 && d.width != 0) {
            Point hs = new Point(d.width / 2, d.height / 2);
            int cw = d.width;
            if (cw <= 16)
                drsel = Toolkit.getDefaultToolkit().createCustomCursor(isf.controls.utils.ResolutionManager.getInstance().loadImage("images/drag.gif").getImage(), hs, "Zoom");
            else if (cw > 16 && cw <= 32)
                drsel = Toolkit.getDefaultToolkit().createCustomCursor(isf.controls.utils.ResolutionManager.getInstance().loadImage("images/drag32.gif").getImage(), hs, "Zoom");
        } else {
            drsel = new Cursor(Cursor.CROSSHAIR_CURSOR);
        }
    }


   /* PSVM(String args[]) {
        JFrame jframe = new JFrame();
        jframe.setSize(1024, 700);
        IndexImagePanel imagepanel = new IndexImagePanel();
        //imagepanel.setImage(new ImageIcon("images/OS_BMLACH3_R_Bi.jpg"));
        Container container = jframe.getContentPane();
        container.add(imagepanel);
        jframe.setVisible(true);
        imagepanel.setImage(new ImageIcon("images/geotest.jpg"));
    }*/
}
