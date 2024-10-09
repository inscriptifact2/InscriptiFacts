package isf.controls.view.containers;

import isf.common.request.RequestProxy;
import isf.common.utils.CacheManagerConstants;
import isf.common.utils.LogManager;
import isf.controls.utils.CursorManager;
import isf.common.utils.InscriptConfigLoader;
import isf.controls.utils.ResolutionManager;
import isf.controls.utils.GlassPaneManager;
import isf.common.utils.PTMConfig;
import isf.controls.controllers.PTMFrameManager;
import isf.controls.models.DesktopDataObject;
//import isf.net.request.RequestDispatcherObject;
import isf.controls.view.controls.SidDesktop;
import isf.controls.view.gui.ISFDesktopManager;
import isf.ptm.formats.PTM;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;

public class JIFrame extends JInternalFrame implements MouseListener, MouseMotionListener {

    private String header,  imgName,  size;
    private int orgLev,  maxLev,  curLev;
    private int wid,  hei,  w,  h;
    String cpx, cpy;
    Point temp, p;
    Point start, end;
    Rectangle oldrect;
    int px, py;
   // private int mode;
    private ViewerPanel jip;
    private boolean active = true;
    private static Cursor zin,  zout,  drsel,  hando,  handc,  light,  old;
    private int index;
    String currentUrl;
    // public boolean isDragging = false,danger = false;
    public boolean updated = false;
    Logger logger = LogManager.getLogger(isf.controls.view.containers.JIFrame.class);
    

    static {
        Dimension d = Toolkit.getDefaultToolkit().getBestCursorSize(32, 32);
        //System.out.println("Dimension:"+d);
        Point hs = new Point(16, 16);

        boolean cursupport = false;

        if (d.height == 0 && d.width == 0) {
            cursupport = false;
        } else {
            cursupport = true;
            hs = new Point(d.width / 2, d.height / 2);
        }
        int cw = d.width;
        if (cursupport) {
            if (cw <= 16) {
                drsel = Toolkit.getDefaultToolkit().createCustomCursor(ResolutionManager.getInstance().loadImage("images/drag.gif").getImage(), hs, "Zoom");

                zin = Toolkit.getDefaultToolkit().createCustomCursor(ResolutionManager.getInstance().loadImage("images/plus.gif").getImage(), hs, "ZoomIn");

                zout = Toolkit.getDefaultToolkit().createCustomCursor(ResolutionManager.getInstance().loadImage("images/minus.gif").getImage(), hs, "ZoomOut");

                hando = Toolkit.getDefaultToolkit().createCustomCursor(ResolutionManager.getInstance().loadImage("images/handO.gif").getImage(), hs, "PanO");
                //hando=new Cursor(Cursor.HAND_CURSOR);

                handc = Toolkit.getDefaultToolkit().createCustomCursor(ResolutionManager.getInstance().loadImage("images/handC.gif").getImage(), hs, "PanC");
                light = Toolkit.getDefaultToolkit().createCustomCursor(ResolutionManager.getInstance().loadImage("images/lightc.gif").getImage(), hs, "Light");
            //handc=new Cursor(Cursor.HAND_CURSOR);
            } else if (cw > 16 && cw <= 32) {
                drsel = Toolkit.getDefaultToolkit().createCustomCursor(ResolutionManager.getInstance().loadImage("images/drag32.gif").getImage(), hs, "Zoom");

                zin = Toolkit.getDefaultToolkit().createCustomCursor(ResolutionManager.getInstance().loadImage("images/plus32.gif").getImage(), hs, "ZoomIn");

                zout = Toolkit.getDefaultToolkit().createCustomCursor(ResolutionManager.getInstance().loadImage("images/minus32.gif").getImage(), hs, "ZoomOut");

                hando = Toolkit.getDefaultToolkit().createCustomCursor(ResolutionManager.getInstance().loadImage("images/handO32.gif").getImage(), hs, "PanO");

                handc = Toolkit.getDefaultToolkit().createCustomCursor(ResolutionManager.getInstance().loadImage("images/handC32.gif").getImage(), hs, "PanC");
                light = Toolkit.getDefaultToolkit().createCustomCursor(ResolutionManager.getInstance().loadImage("images/lightc32.gif").getImage(), hs, "Light");

            }
        } else {
            drsel = new Cursor(Cursor.CROSSHAIR_CURSOR);
            zin = new Cursor(Cursor.N_RESIZE_CURSOR);
            zout = new Cursor(Cursor.S_RESIZE_CURSOR);
            hando = new Cursor(Cursor.HAND_CURSOR);
            handc = new Cursor(Cursor.HAND_CURSOR);
            light = new Cursor(Cursor.DEFAULT_CURSOR);
        }
    }

    public JIFrame() {
        super("", true, true, true, true);
        setMinimumSize(new Dimension(400, 400));
    }

    public JIFrame(DesktopDataObject ddo) { //String ret

        super(ddo.getHeader(), true, true, true, true);
        header = ddo.getDisplayName();
        imgName = ddo.getFileName();
        final DesktopDataObject dsk = ddo;
        setMinimumSize(new Dimension(400, 400));
        jip = null;
        if (imgName.endsWith(".ptm") || imgName.endsWith(".rti")|| imgName.endsWith(".mview")) {
            final PTMConfig config = ddo.getConfig();

            setCopy(ddo.isCopy());
            jip = new ViewerPanel("RTI");
            cpx = "0.5";
            cpy = "0.5";

            Thread r = new Thread() {

                public void run() {
                    if (config != null) {
                        ((PTMPanel) jip.getPanel()).addImage(config);
                    } else {
                        ((PTMPanel) jip.getPanel()).addImage(dsk);
                    }
                }
            };
            r.setPriority(9);
            SwingUtilities.invokeLater(r);



        } else {
            jip = new ViewerPanel("SID");
        }

        setContentPane(jip);
        jip.getPanel().addMouseListener(this);


    }

    public JIFrame(DesktopDataObject ddo, PTM ptm) {  //String ret,

        super(ddo.getHeader(), true, true, true, true);
        header = ddo.getDisplayName();
        imgName = ddo.getFileName();
        setMinimumSize(new Dimension(400, 400));
        jip = null;
        final PTMConfig config = ddo.getConfig();
        jip = new ViewerPanel("RTI");
        setContentPane(jip);
        cpx = "0.5";
        cpy = "0.5";
        setCopy(ddo.isCopy());

        ((PTMPanel) jip.getPanel()).addImage(config, ptm);
        jip.getPanel().addMouseListener(this);
        String name = ptm.getName();

        name = name.substring(name.lastIndexOf(File.separator) + 1);

        if (name.indexOf(".") > -1) {
            name = name.substring(0, name.indexOf("."));
        }
        PTMFrameManager.getInstance().addCopies(name, this);


    }
    

    public void getDefVal() {
        if (imgName == null || imgName.length() == 0 || imgName.trim().equalsIgnoreCase("")) {
            // System.out.println(" found null image name in defprop");
            return;
        }
        byte abyte[] = null;
        cpx = "0.5";
        cpy = "0.5";
        String cat = InscriptConfigLoader.getInstance().getProperty("Locsid");
        if (cat == null) {
            cat = "Inscript";
        }
        abyte = (byte[]) RequestProxy.getRequestDispatcher().getImageProperties("cat=" + cat + "&item=/" + imgName + "&props=Image&style=none");

        //abyte=(byte[])(new ImageRequestObject().getImageProperties("cat=Inscript&item=/"+imgName+"&props=Image&style=none"));
        if (abyte != null) {
            String obj = new String(abyte);
            int i = obj.indexOf("size=\"");
            int p = obj.indexOf("\"", i + 6);
            if (i != -1 && p != -1) {
                size = obj.substring(i + 6, p);
            } else {
                logger.debug("Size is null or :" + size);
            }
            i = obj.indexOf("width=\"");
            p = obj.indexOf("\"", i + 7);
            if (i != -1 && p != -1) {
                wid = Integer.parseInt(obj.substring(i + 7, p));
            } else {
                logger.debug("width  is null or :" + wid);
            }
            i = obj.indexOf("height=\"");
            p = obj.indexOf("\"", i + 8);
            if (i != -1 && p != -1) {
                hei = Integer.parseInt(obj.substring(i + 8, p));
            } else {
                logger.debug("height is null or :" + hei);
            }
            i = obj.indexOf("numlevels=\"");
            p = obj.indexOf("\"", i + 11);
            if (i != -1 && p != -1) {
                maxLev = Integer.parseInt(obj.substring(i + 11, p));
            } else {
                logger.debug("maxLev is null or :" + maxLev);
            }
            ((SidPanel) jip.getPanel()).setImgWidth(wid);
            ((SidPanel) jip.getPanel()).setImgHeight(hei);
            ((SidPanel) jip.getPanel()).setMaxLevel(maxLev);
            ((SidPanel) jip.getPanel()).setImgName(imgName);
            ((SidPanel) jip.getPanel()).setFileSize(size);


            double tw = wid;
            double th = hei;
            Rectangle r = jip.getBounds();
            w = r.width;
            h = r.height;


            for (orgLev = 0; th >= h || //for (orgLev = 0; th >= hd ||
                    tw >= w; orgLev++) {                               // tw >= wd; orgLev++) {
                //System.out.print("th:"+th+"::tw:"+tw+":--\n");

                th /= 2;
                tw /= 2;
            //++orgLev;
            }
            // orgLev++;
            curLev = orgLev;
        }
// updateImg();
    }

  

    public void update() {
        if (!jip.getPanel().isPTMPanel()) {
            getDefVal();
            updateImg();
        }
    }

    public void reset() {
        Cursor oldCursor = getCursor();
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        //  CursorManager.changeCursor(new Cursor(Cursor.WAIT_CURSOR));
        if (!jip.getPanel().isPTMPanel()) {
            cpx = "0.5";
            cpy = "0.5";
            setLevel(orgLev, 0);

        } else {
            /* Dimension d = ((PTMPanel)(jip.getPanel())).getCalculatedDisplaySize();
            ((PTMPanel)(jip.getPanel())).setPTMView(d);
            jip.getPanel().repaint();*/
            ((PTMPanel) (jip.getPanel())).fitToSize();
        }
        setCursor(oldCursor);
    //  CursorManager.changeCursor(oldCursor);

    }

    public void setLevel(int lev, int reduce) {
        if (jip.getPanel() instanceof SidPanel) {
            curLev = lev - reduce;
            ((SidPanel) jip.getPanel()).setCurLevel(curLev);

            updateImg();
        }
    }

    public void mousePressed(MouseEvent me) {
        int mode = PTMFrameManager.getMode();

        if (!active || mode == SidDesktop.PTM) {
            return;
        }
        old = getCursor();
        if (mode == SidDesktop.PAN) {
            setCursor(handc);
        }
        start = new Point(me.getX(), me.getY());
        if(!jip.getPanel().isPTMPanel()){
            //jip.getPanel().setStart(start, mode);
            jip.getPanel().setStart(start);
        }

    }
/*
    public void setMouseMode(int i, MouseEvent e) {
        //  System.out.println("jiframe presses");
        if (!active)// || working)
        {
            return;
        }
        mode = i;
        old = getCursor();
        if (mode == 4) {
            setCursor(handc);
        }
        start = new Point(e.getX(), e.getY());
        jip.getPanel().setStart(start, mode);

    }*/

    public void mouseClicked(MouseEvent me) {
    }
    private JComponent comp = null;
    private Rectangle rect = null;
    private boolean glassPaneShowing = false;

    public void mouseEntered(MouseEvent me) {
         int mode = PTMFrameManager.getMode();

        if (CursorManager.wait) {
            return;
        } else if (ISFDesktopManager.isDragging) {
            GlassPaneManager gpm = GlassPaneManager.getInstance();
            glassPaneShowing = gpm.isShowing();
            if (glassPaneShowing) {
                comp = (JComponent) gpm.getComponent();
                rect = comp.getBounds();
                gpm.hideAll();
            }
            return;
        }

        if (!active) {
            old = new Cursor(Cursor.DEFAULT_CURSOR);
        }
        switch (mode) {
            case 1:
                setCursor(zin);
                break;
            case 2:
                setCursor(zout);
                break;
            case 3:
                setCursor(drsel);
                break;
            case 4:
                setCursor(hando);
                break;
            case SidDesktop.PTM:
                if (getImagePanel().isPTMPanel()) {
                    setCursor(light);
                } else {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
                break;
            default:
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                break;

        }


    }

    public void mouseExited(MouseEvent me) {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public void mouseReleased(MouseEvent me) {
        int mode = PTMFrameManager.getMode();

        if (CursorManager.wait || mode == SidDesktop.PTM) {
            return;
        } else if (ISFDesktopManager.isDragging) {
            if (glassPaneShowing) {
                //  GlassPaneManager.getInstance().showComponent(comp, rect);
                glassPaneShowing = false;
            }
            return;
        }
        if (!active) {
            isActive(true);

            switch (mode) {
                case 1:
                    setCursor(zin);
                    break;
                case 2:
                    setCursor(zout);
                    break;
                case 3:
                    setCursor(drsel);
                    break;
                case 4:
                    setCursor(hando);
                    break;
                case SidDesktop.PTM:
                    if (getImagePanel().isPTMPanel()) {
                        setCursor(light);
                    } else {
                        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    }
                    break;
                default:
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    break;

            }
            return;
        }

        end = new Point(me.getX(), me.getY());
        if (mode >= 5) {
            return;
        }
        if(!jip.getPanel().isPTMPanel())
            jip.getPanel().setEnd(end);

        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        if (SwingUtilities.isLeftMouseButton(me) ) { //&& !jip.getPanel().isPTMPanel()
            calculateDiff();
        }
        Runnable r = new Runnable() {

            public void run() {
                while (jip.getPanel().working) {
                }
                ;
                setCursor(old);
            }
        };
        SwingUtilities.invokeLater(r);
    // setCursor(old);

    }

    private void updateImg() {
        if (imgName == null || imgName.length() == 0 || imgName.trim().equalsIgnoreCase("")) {
            // System.out.println(" found null image name in image");
            return;
        }
        if (!jip.getPanel().isPTMPanel()) {

            Rectangle r = jip.getPanel().getBounds();

            w = r.width;
            h = r.height;
            String cat = InscriptConfigLoader.getInstance().getProperty("Locsid");
            if (cat == null) {
                cat = "Inscript";
            }
            currentUrl =
                    "cat=" + cat + "&item=/" + imgName + "&bg=000000&lev=" + curLev + "&wid=" + w + "&hei=" + h + "&cp=" + cpx + "," + cpy;
            //  jip.addImage(new ImageRequestObject().fetchImage(currentUrl));
            ImageIcon ic = RequestProxy.getRequestDispatcher().fetchImage(currentUrl);
            //ImageIcon ic = new ImageIcon();
            if (ic != null) {
                jip.getPanel().addImage(ic);
            }
            updated = true;
        } 
    }

    private float levelCvt(int val, int scale) {
        if (curLev > 0) {
            val <<= curLev;
        } else {
            val >>= -curLev;
        }
        return (float) ((float) val / (float) scale);
    }

    private void calculateDiff() {
         int mode = PTMFrameManager.getMode();

        Rectangle r = jip.getPanel().getBounds();
        /* if (SidDesktop.defMode > 0) {
        mode = SidDesktop.defMode;
        }
        SidDesktop.defMode = 0;*/
        //mode = SidDesktop.mode;

        w = r.width;
        h = r.height;
        int lev = 0;
        if (start.equals(end)) {
            px = start.x - (r.width / 2);
            py = start.y - (r.height / 2);

        } else {
            px = start.x - end.x;
            py = start.y - end.y;
            if (mode == 3) {
                int wx = Math.abs(px);
                int hy = Math.abs(py);
                /*  if (start.x > end.x) {
                px = start.x - (wx / 2);
                } else {
                px = start.x + (wx / 2);
                }
                if (start.y > end.y) {
                py = start.y - (hy / 2);
                } else {
                py = start.y + (hy / 2);
                }*/
                px = start.x - (px / 2);
                py = start.y - (py / 2);

                //px = px - (r.width / 2);
                //py = py - (r.height / 2);
                px = px - (r.width / 2);
                py = py - (r.height / 2);


                ///// calculate ratio of box
                for (; (wx < r.width) && (hy < r.height); lev++) {
                    wx *= 2;
                    hy *= 2;
                }
                lev--;

            }
        }


        cpx = (Float.parseFloat(cpx) + levelCvt(px, wid)) + "";
        cpy = (Float.parseFloat(cpy) + levelCvt(py, hei)) + "";


        switch (mode) {
            case 1:
                if (!SidDesktop.masked) {
                    setLevel(curLev, 1);
                } else {
                    zoomOut();
                }
                break;
            case 2:
                if (!SidDesktop.masked) {
                    zoomOut();
                } else {
                    setLevel(curLev, 1);
                }
                break;
            case 3:
                setLevel(curLev, lev);
                break;
            default:
                setLevel(curLev, 0);
                break;
        }
        if (jip.getPanel().isAtMaxLevel() && !jip.getPanel().getMessageShown() && (mode == SidDesktop.ZIN || mode == SidDesktop.SZ)) {
           // JOptionPane.showMessageDialog(this, "The image is already at 100%.  Zooming further will result in an interpolated image.", "Information", 1);
            JOptionPane.showMessageDialog(this, "Images enlarged beyond 130% are interpolated. ", "Information", 1);
            //fix for 21 NEW 
            jip.getPanel().setMessageShown(true);
        /* if (jip.getPanel().isPTMPanel()) {
        ((PTMPanel) jip.getPanel()).setDrawRect(false);
        jip.getPanel().repaint();
        }*/

        }

    }

    private void zoomOut() {
        if (!jip.getPanel().isPTMPanel()) {
            if (curLev == maxLev) {
                JOptionPane.showMessageDialog(this, "You are already at the last level.", "Information", 1);
            } else {
                setLevel(curLev + 1, 0);
            }
        }
    }

    public void setBounds(int x, int y, int width, int height) {

        int oldWidth = getSize().width;
        int oldHeight = getSize().height;
        //CursorManager.changeCursor(new Cursor(Cursor.WAIT_CURSOR));
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
//getDesktopPane().setCursor(new Cursor(Cursor.WAIT_CURSOR));
        //System.out.println("called");
        super.setBounds(x, y, width, height);

        //System.out.println("SetBounds");
        //  if(w==width && h==height && SidDesktopManager.mode)
        if ((oldWidth != width || oldHeight != height) && updated) {
            updateImg();
        }
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    // CursorManager.changeCursor(Cursor.DEFAULT_CURSOR);


    }

    public void isActive(boolean t) {
        active = t;
        jip.getPanel().setActive(t);
    //System.out.println("Active Called : "+getTitle()	+" is "+t);
    }

    public String getRet() {
        //return retVal;
        return header;
    }

    public void setIndex(int ind) {
        index = ind;
    }

    public int getIndex() {
        return index;
    }

    public String getProperties() {

        return jip.getPanel().getDetails();


    }

    public void resetAll() {

        header = imgName = size = ""; //retVal = ""

        orgLev = maxLev = curLev = 0;
        wid = hei = w = h = 0;
        cpx = cpy = "0.5";
        start = end = new Point(0, 0);
        px = py = 0;
       // mode = 0;
        setVisible(false);
        if (!jip.getPanel().isPTMPanel()) {
            jip.getPanel().addImage((ImageIcon) null);
        } else {
            ((PTMPanel) jip.getPanel()).reset();
        }
        active = true;
        currentUrl = "";
        updated = false;


    }

    public void saveAs(String str) {
       /* String fname = imgName.substring(0, imgName.indexOf(".") + 1);
        fname = fname + str;
        jip.getPanel().saveImage(fname, str);
        if (fname != null) {
            JOptionPane.showMessageDialog(this, fname, ":: Status ::", 1);
        }*/
        
        String s1 = imgName.indexOf(File.separator) <= -1 ? imgName : imgName.substring(imgName.lastIndexOf(File.separator)+1);
        s1 = s1.indexOf(".") <= -1 ? s1 : s1.substring(0, s1.indexOf("."));
       
        Cursor cursor = getCursor();
        setCursor(new Cursor(3));
             
        s1 = (new StringBuilder()).append(CacheManagerConstants.downloadFolderName).append(File.separator).append(s1).append("-").append(System.currentTimeMillis()).append(".").append(str).toString();
        s1 = jip.getPanel().saveImage(s1, str);
        if(s1 != null && !s1.trim().equalsIgnoreCase(""))
        {
            JOptionPane.showMessageDialog(this, s1, ":: Status ::", 1);
        } else
        if(s1.equalsIgnoreCase("error"))
        {
            JOptionPane.showMessageDialog(this, "File could not be saved", ":: Status ::", 0);
        }
        setCursor(cursor);
        
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    public String getImgName() {
        return imgName;
    }

    public void setMode(int i) {


     /////////////   mode = i;
    ///////////////    jip.getPanel().setMode(i);
        ///reviewCursor();
        if (!active) {
            old = new Cursor(Cursor.DEFAULT_CURSOR);
        // return;
        }
        int mode = i;
        switch (mode) {
            case 1:
                // CursorManager.changeCursor(zin);
                setCursor(zin);
                break;
            case 2:
                // CursorManager.changeCursor(zout);
                setCursor(zout);
                break;
            case 3:
                // CursorManager.changeCursor(drsel);
                setCursor(drsel);
                break;
            case 4:
                // CursorManager.changeCursor(hando);
                setCursor(hando);
                break;
            case SidDesktop.PTM:
                if (jip.getPanel().isPTMPanel()) {
                    setCursor(light);
                } else {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
                break;
            default:
                // CursorManager.changeCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                break;

        }


    }

    public ImagePanel getImagePanel() {
        return jip.getPanel();
    }
    private boolean copy = false;

    public boolean isCopy() {
        return copy;
    }

    public void setCopy(boolean copy) {
        this.copy = copy;
    }
}


