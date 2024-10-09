package com.isf.app.view.gui;

import java.awt.*;
import javax.swing.DefaultDesktopManager;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class SidDesktopManager extends DefaultDesktopManager {

    public int dragMode;
    private transient Rectangle currentBounds;
    private transient Graphics desktopGraphics;
    private transient Point currentLoc;
    public static boolean drag = false;
    public static boolean mode = false;
    private transient Image desktopImg;
    private JDesktopPane p;
    static Robot rbt = null;
    Rectangle r;
    Point cp;
    private transient Image memImg;
    Logger logger = LogManager.getLogger(com.isf.app.view.gui.SidDesktopManager.class);

    public SidDesktopManager(JDesktopPane jdesktoppane) {
        dragMode = 0;
        currentBounds = null;
        desktopGraphics = null;
        currentLoc = null;
        desktopImg = null;
        p = null;
        memImg = null;
        p = jdesktoppane;
        doUpdate(false);
    }

    public void beginDraggingFrame(JComponent jcomponent) {
        if (jcomponent instanceof JInternalFrame) {
            setupDragMode(jcomponent);
            if (dragMode == 2) {
                super.beginDraggingFrame(jcomponent);
            }
        }
    }

    private void setupDragMode(JComponent jcomponent) {
        drag = true;
        if (p != null) {
            String s = (String) p.getClientProperty("JDesktopPane.dragMode");
            if (s != null && s.equals("outline")) {
                dragMode = 1;
            } else if (s != null && s.equals("faster") && (jcomponent instanceof JInternalFrame)) {
                dragMode = 2;
            } else if (p.getDragMode() == JDesktopPane.OUTLINE_DRAG_MODE) {
                dragMode = 1;
            } else if (p.getDragMode() == JDesktopPane.LIVE_DRAG_MODE && (jcomponent instanceof JInternalFrame)) {
                dragMode = 2;
            } else {
                dragMode = 0;
            }
        }
    }

    private void updateOffScr() {
        try {
            memImg = rbt.createScreenCapture(new Rectangle(cp.x, cp.y, r.width, r.height));
        } catch (Exception exception) {
            //System.out.println("Error :" + exception);
            logger.error(exception);
        }
        drag = false;
    }

    public void dragFrame(JComponent jcomponent, int i, int j) {
        if (jcomponent instanceof JInternalFrame) {
            if (dragMode == 1) {
                if (drag) {
                    updateOffScr();
                }
                desktopGraphics.setColor(Color.black);
                desktopGraphics.fillRect(0, 0, r.width, r.height);
                desktopGraphics.drawImage(memImg, 0, 0, p);
                if (p != null) {
                    Graphics g = p.getGraphics();
                    desktopGraphics.setColor(Color.yellow);
                    desktopGraphics.drawRect(i, j, jcomponent.getWidth() - 1, jcomponent.getHeight() - 1);
                    desktopGraphics.setPaintMode();
                    desktopGraphics.dispose();
                    currentLoc = new Point(i, j);
                    g.drawImage(desktopImg, 0, 0, p);
                    g.dispose();
                }
            } else {
                setBoundsForFrame(jcomponent, i, j, jcomponent.getWidth(), jcomponent.getHeight());
                //System.out.println("Drag Mode != 1");
            }
        }
    }

    public void endDraggingFrame(JComponent jcomponent) {
        if (jcomponent instanceof JInternalFrame) {
            if (currentLoc != null) {
                jcomponent.setBounds(currentLoc.x, currentLoc.y, jcomponent.getWidth(), jcomponent.getHeight());
                jcomponent.validate();
                currentLoc = null;
            }
            p.setCursor(new Cursor(0));
            //CursorManager.changeCursor(Cursor.WAIT_CURSOR );
            if (drag) {
                drag = false;
            }
            mode = false;
        }
    }

    public void beginResizingFrame(JComponent jcomponent, int i) {
        if (jcomponent instanceof JInternalFrame)
            setupDragMode(jcomponent);
    }

    public void resizeFrame(JComponent jcomponent, int i, int j, int k, int l) {
        if (jcomponent instanceof JInternalFrame) {
            if (dragMode == 0 || dragMode == 2) {
                setBoundsForFrame(jcomponent, i, j, k, l);
            } else {
                if (drag) {
                    updateOffScr();
                }
                desktopGraphics.setColor(Color.black);
                desktopGraphics.fillRect(0, 0, r.width, r.height);
                desktopGraphics.drawImage(memImg, 0, 0, p);
                if (p != null) {
                    Graphics g = p.getGraphics();
                    desktopGraphics.setColor(Color.yellow);
                    desktopGraphics.drawRect(i, j, k - 1, l - 1);
                    desktopGraphics.setPaintMode();
                    currentBounds = new Rectangle(i, j, k, l);
                    g.drawImage(desktopImg, 0, 0, p);
                    desktopGraphics.dispose();
                    g.dispose();
                }
            }
        }
    }

    public void endResizingFrame(JComponent jcomponent) {
        if (jcomponent instanceof JInternalFrame) {
            if (currentBounds != null) {
                jcomponent.setBounds(currentBounds.x, currentBounds.y, currentBounds.width, currentBounds.height);
                jcomponent.validate();
                currentBounds = null;
            }
            p.setCursor(new Cursor(0));
            //CursorManager.changeCursor(Cursor.WAIT_CURSOR );
            if (drag) {
                drag = false;
            }
            mode = true;
        }
    }

    public void doUpdate(boolean flag) {
        if (p != null) {
            r = p.getBounds();
            if (p.isShowing()) {
                cp = p.getLocationOnScreen();
                desktopImg = p.createImage(r.width, r.height);
                desktopGraphics = desktopImg.getGraphics();
                desktopGraphics.setColor(Color.black);
                desktopGraphics.dispose();
            }
        }
        if (flag) {
            updateOffScr();
        }
    }

    static {
        try {
            rbt = new Robot();
        } catch (Exception exception) {
        }
    }
}
