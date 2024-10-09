package isf.controls.view.controls;

//import com.isf.utils.LogManager;
import isf.common.utils.LogManager;
import isf.controls.controllers.ActiveXListenerManager;
import isf.controls.controllers.KeyProcessManager;
import isf.controls.controllers.PTMFrameManager;
import isf.controls.controllers.ToolbarManager;
import isf.controls.models.DesktopDataObject;
import isf.controls.utils.CursorManager;
import isf.controls.utils.GlassPaneManager;
import isf.controls.view.containers.JIFrame;
import isf.controls.view.gui.ISFDesktopManager;
import isf.controls.view.listeners.ViewDesktopListener;
import isf.ptm.controls.PTMWindow;

import isf.ptm.formats.PTM;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import java.awt.*;
import java.beans.PropertyVetoException;
import java.io.File;
import java.util.Vector;
import org.apache.log4j.Logger;

public class SidDesktop extends JPanel
        implements InternalFrameListener, ViewDesktopListener {
Logger log = LogManager.getLogger(isf.controls.view.controls.SidDesktop.class);
    public static final int ZIN = 1;
    public static final int ZOUT = 2;
    public static final int SZ = 3;
    public static final int PAN = 4;
    public static final int PROP = 6;
    public static final int FIT = 5;
    public static final int ABT = 7;
    public static final int SAVE = 8;
    public static final int PTM = 9;
    public static final int SELECT = 0;
  //  public static int mode;
  //  public static int defMode = 0;
    public static boolean masked = false;
    private Vector avail;
    private JDesktopPane jp;
    JIFrame frames[];
    int count;
    public SidToolbar sidtoolbar;


    public SidDesktop() {
       
        frames = new JIFrame[5];
        count = 5;

        setLayout(new BorderLayout(0, 0));
        setBackground(Color.black);
        jp = new JDesktopPane();
        jp.setBackground(Color.black);
        add(jp, "Center");
        sidtoolbar = new SidToolbar();
        add(sidtoolbar, "North");
        sidtoolbar.addVDListener(this);
        sidtoolbar.setBackground(Color.black);

        jp.setDesktopManager(new ISFDesktopManager());
        jp.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
        jp.putClientProperty("DRAG", "false");
        KeyProcessManager.getInstance().addKeyHandler(sidtoolbar);
        ToolbarManager.getInstance().registerToolbar(sidtoolbar);

    }

    /*  public boolean isFocusTraversable() {
    return true;
    }*/
    public Insets getInsets() {
        return new Insets(0, 0, 0, 0);
    }

    public Border getBorder() {
        return new LineBorder(Color.white, 1);
    }

    public String addImage(DesktopDataObject ddo) {  //, String s2

        if (avail.size() == 0) {
            return "ERROR";
        }

        int j = ((Integer) avail.elementAt(0)).intValue();
        //  System.out.println("frames :"+frames[j]);
        if (frames[j] != null) {
            frames[j].resetAll();
            frames[j] = null;
            System.gc();
        }
        if (frames[j] == null) {
 
            frames[j] = new JIFrame(ddo); //s2

            frames[j].setBounds(j * 25, j * 25, 400, 400);
            frames[j].setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
            frames[j].addInternalFrameListener(this);
            jp.add(frames[j], JLayeredPane.DEFAULT_LAYER);

        }

        try {
            frames[j].setSelected(true);
        } catch (PropertyVetoException propertyvetoexception2) {
            //System.out.println(propertyvetoexception2);
            log.error(propertyvetoexception2);
        }
        frames[j].setVisible(false);
        frames[j].update(); //marked as not required for PTM may be required for SID

        frames[j].setVisible(true);
        frames[j].moveToFront();
        frames[j].setClosable(true);
        frames[j].setIconifiable(true);
        frames[j].setMaximizable(true);
        frames[j].setResizable(true);
        frames[j].setIndex(j);
        if (frames[j].getImagePanel().isPTMPanel()) {
            frames[j].setFrameIcon(isf.controls.utils.ResolutionManager.getInstance().getImageIcon("images/flash.gif"));
            ISFDesktopManager.addPTMs();
        } else {
            frames[j].setFrameIcon(isf.controls.utils.ResolutionManager.getInstance().getImageIcon("images/camera.gif"));
        }
        avail.removeElementAt(0);
        return new String("success");
    }
/*
    public void setMode(int i, MouseEvent e) {
      
        JIFrame frame = (JIFrame) jp.getSelectedFrame();
        frame.isActive(true);
        frame.setMouseMode(i, e);

        for (int j = 0; j < frames.length; j++) {
            if (frames[j] != null && !frame.equals(frames[j])) {
                frames[j].setMode(i);
            }
        }

    }
 * */

    public void performAction(int i) {
        JIFrame jiframe = (JIFrame) jp.getSelectedFrame();
        if (i == 7) {
            JOptionPane.showMessageDialog(this, about,
                    ":: About Viewer ::", 0, isf.controls.utils.ResolutionManager.getInstance().loadImage("images/isf.jpg"));
            return;
        }
        if (jiframe != null) {
            if (i == 5) {
                jiframe.reset();
            } else if (i == 6) {
                String s = jiframe.getProperties();
                JOptionPane.showMessageDialog(this, s, ":: Image Properties ::", 0, isf.controls.utils.ResolutionManager.getInstance().loadImage("images/isf.jpg"));
            } else if (i == 8) {
                /////TODO: modified changes for solving the FullSize of PTM IMAGES
                /*String as[] = {
                    "JPEG"
                };
                String s1 = (String) JOptionPane.showInputDialog(this, "The image can be saved as :", ":: Save Image As? ::", 3, isf.controls.utils.ResolutionManager.getInstance().loadImage("images/isf.jpg"), as, as[0]);
                if (s1 != null) {*/
                
                    jiframe.saveAs("jpeg");
                //}
                ////// TILL HERE @ 14thFeb 2014
            } else if (i != SidDesktop.PTM) {
              //  mode = i;
                for (int j = 0; j < frames.length; j++) {
                    if (frames[j] != null) {
                        frames[j].setMode(i);
                    }
                }
            } else {
                for (int j = 0; j < frames.length; j++) {
                    if (frames[j] != null && frames[j].getImagePanel().isPTMPanel()) {
                        frames[j].setMode(i);
                    }
                }
            }

            jiframe.isActive(true);
        }
    }

  /*  public void setDefMode(int i) {
        defMode = i;
    }*/
/*
    PSVM(String args[]) {
        SidDesktop siddesktop = new SidDesktop();
        JFrame jframe = new JFrame();
        jframe.setSize(700, 600);
        jframe.setVisible(true);
        jframe.setContentPane(siddesktop);
        siddesktop.reset();

    }
    
    */
    Cursor current = null;

    public void internalFrameClosing(InternalFrameEvent internalframeevent) {
        current = CursorManager.getCursor();
        CursorManager.changeCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (!((JIFrame) internalframeevent.getInternalFrame()).getImagePanel().isLoading()) {
            ((JIFrame) internalframeevent.getInternalFrame()).dispose();

        }


    }

    public void internalFrameClosed(InternalFrameEvent internalframeevent) {

        final JIFrame frame = (JIFrame) internalframeevent.getInternalFrame();
        String name = frame.getImgName();
        name = name.substring(name.lastIndexOf(File.separator) + 1);
        if (name.indexOf(".") > -1) {
            name = name.substring(0, name.indexOf("."));
        // frame.resetAll();
        }
        ISFDesktopManager.closePTMs();
        ActiveXListenerManager.deleted(frame.getRet());
        updateCount(frame.getIndex());
        if (avail.size() == 5) {
            sidtoolbar.setCommonButtons(false);
            sidtoolbar.setPTMButtons(false);
        }
        if (GlassPaneManager.getInstance().isShowing()) {
            GlassPaneManager.getInstance().hideAll();
        }
        if (frame.getImagePanel().isPTMPanel()) {
            PTMFrameManager.getInstance().releaseChangeListener((PTMWindow) (frame.getImagePanel()));
            PTMFrameManager.getInstance().setPTMWindow(null);
            sidtoolbar.setPTMButtons(false);
        }

        if (!frame.isCopy()) {
            Vector v = PTMFrameManager.getInstance().getCopies(name);
            for (int i = 0; i < v.size(); i++) {
                JInternalFrame f = (JInternalFrame) v.get(i);
                f.dispose();
            }
            PTMFrameManager.getInstance().removeCopies(name);
        }


        System.gc();
        CursorManager.changeCursor(current);

    //  ThumbDeselectorManager.getInstance().deleteSelection(((JIFrame) internalframeevent.getInternalFrame()).getImgName());
    }

    public void internalFrameOpened(InternalFrameEvent internalframeevent) {
    }

    public void internalFrameIconified(InternalFrameEvent internalframeevent) {
        long start = System.currentTimeMillis();
        final JIFrame frame = (JIFrame) internalframeevent.getInternalFrame();
        if (frame.isCopy()) {
            frame.dispose();
        } else {
            internalframeevent.getInternalFrame().getDesktopIcon().setVisible(false);
            ActiveXListenerManager.minimized(frame.getRet());
            updateCount(frame.getIndex());
        }
        System.gc();

        //System.out.println("Total Time to iconify:" + (System.currentTimeMillis() - start));


    }

    public void internalFrameDeiconified(InternalFrameEvent internalframeevent) {
        ((JIFrame) internalframeevent.getInternalFrame()).isActive(false);
        ActiveXListenerManager.activated(((JIFrame) internalframeevent.getInternalFrame()).getRet());
    }

    public void internalFrameActivated(InternalFrameEvent internalframeevent) {
        JIFrame frame = ((JIFrame) internalframeevent.getInternalFrame());
        frame.isActive(false);

        if (frame.getImagePanel().isPTMPanel()) {
            PTMFrameManager.getInstance().setPTMWindow((PTMWindow) (frame.getImagePanel()));
           // int mode = PTMFrameManager.getMode();
           // performAction(PTM);
            
            sidtoolbar.setPTMButtons(true);

             
        } else {
            PTMFrameManager.getInstance().setPTMWindow(null);
            sidtoolbar.setPTMButtons(false);
        }
        sidtoolbar.setCommonButtons(true);
        if (frame.getImagePanel().isHSHPanel()) {
            PTMFrameManager.getInstance().setDiffuseOperation(false, false);
            PTMFrameManager.getInstance().setLightOperation(false);

        } else {
            PTMFrameManager.getInstance().setDiffuseOperation(true, false);
            PTMFrameManager.getInstance().setLightOperation(true);
        }

        ActiveXListenerManager.activated(((JIFrame) internalframeevent.getInternalFrame()).getRet());
    }

    public void internalFrameDeactivated(InternalFrameEvent internalframeevent) {
        ((JIFrame) internalframeevent.getInternalFrame()).isActive(false);
    }

    public void resize(Rectangle rectangle) {
        setSize(rectangle.width, rectangle.height);
        revalidate();
        validate();
        repaint();
    //   ((SidDesktopManager) jp.getDesktopManager()).doUpdate(false);

    }

    public String getActive() {
        return ((JIFrame) jp.getSelectedFrame()).getRet();
    }

    public void setActive(String s) {
        for (int i = 0; i < count; i++) {
            if (frames[i].getRet() == null || !frames[i].getRet().equals(s)) {
                continue;
            }
            frames[i].moveToFront();
            try {
                frames[i].setSelected(true);
                frames[i].isActive(true);
            } catch (PropertyVetoException propertyvetoexception) {
                //System.out.println(propertyvetoexception);
                log.error(propertyvetoexception);
            }
            break;
        }

    }

    public String checkImage(DesktopDataObject desktopdataobject) {  //String s

        if (desktopdataobject.isAdded()) {
            if (getActive().equals(desktopdataobject.getFileName())) {
                JOptionPane.showMessageDialog(this, "Image is already shown in the control", "Selection Error", 0);
            } else {
                // setActive(s);
                setActive(desktopdataobject.getDisplayName());
            }
            return "";
        }
        String s1 = addImage(desktopdataobject);//,s

        if (s1.equalsIgnoreCase("ERROR")) {
            JOptionPane.showMessageDialog(this, " Maximum 5 images can be opened in viewer.", "Too Many Images", 0);
            ActiveXListenerManager.deleted(desktopdataobject.getDisplayName());
        } else if (s1.equalsIgnoreCase("Success")) {
            desktopdataobject.setAdded(true);
        }
        return s1;
    }

    public void updateCount(int i) {
        /*  while(avail.contains(new Integer(i)))
        i++;*/
        if (!avail.contains(new Integer(i))) {


            if (avail.size() > 0) {
                int j = 0;
                for (j = 0; j < avail.size(); j++) {
                    int cur = ((Integer) avail.elementAt(j)).intValue();
                    if (i >= cur) {
                        continue;
                    } else {
                        avail.insertElementAt((new Integer(i)), j);
                        break;
                    }
                }
                if (j >= avail.size()) {
                    avail.addElement(new Integer(i));
                }
            } else {
                avail.addElement(new Integer(i));
            }
        }
        if (frames[i] != null) {
            frames[i].resetAll();
        }
        frames[i] = null;
    // System.out.println("after update:" + avail + " added index:" + i);
    }

    public void reset() {

        avail = new Vector();
        for (int i = 0; i < 5; i++) {
            avail.addElement(new Integer(i));
            if (frames[i] != null) {
                frames[i].resetAll();
            }
        }
        ISFDesktopManager.resetPTMs();
        PTMFrameManager.getInstance().releaseChangeListeners();
        sidtoolbar.updateDefault();


    }

    public void deleteControl() {
    }

    public String addImage(DesktopDataObject ddo, PTM ptm) {  //s2

        if (avail.size() == 0) {
            return "ERROR";
        }

        int j = ((Integer) avail.elementAt(0)).intValue();

        if (frames[j] != null) {
            frames[j].resetAll();
            System.gc();
            frames[j] = null;
        }
        frames[j] = new JIFrame(ddo, ptm); //s2

        frames[j].setBounds(j * 25, j * 25, 400, 400);
        frames[j].setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
        frames[j].addInternalFrameListener(this);

        jp.add(frames[j], JLayeredPane.DEFAULT_LAYER);


        try {
            frames[j].setSelected(true);
        } catch (PropertyVetoException propertyvetoexception2) {
            //System.out.println(propertyvetoexception2);
            log.error(propertyvetoexception2);
        }
        frames[j].setVisible(false);
        frames[j].update(); //marked as not required for PTM may be required for SID

        frames[j].setVisible(true);
        frames[j].moveToFront();
        frames[j].setClosable(true);
        frames[j].setIconifiable(true);
        frames[j].setMaximizable(true);
        frames[j].setResizable(true);
        frames[j].setIndex(j);

        if (frames[j].getImagePanel().isPTMPanel()) {
            frames[j].setFrameIcon(isf.controls.utils.ResolutionManager.getInstance().getImageIcon("images/flash.gif"));
        } else {
            frames[j].setFrameIcon(isf.controls.utils.ResolutionManager.getInstance().getImageIcon("images/camera.gif"));
        }
        avail.removeElementAt(0);
        return new String("success");
    }
    static boolean loading = false;

    public static boolean canLoad() {
        boolean flag = false;
        if (!loading) {
            loading = true;
            flag = true;
        }
        return flag;
    }

    public static void stopLoad() {
        System.out.println("stopping loading");
        loading = false;
    }
    private static String about = "<html> InscriptiFact Viewer : MrSID and RTI Functionality <br>" +
            "<br>MrSID is a file format (filename extension .sid) developed and patented by LizardTech for<br> " +
            "encoding georeferenced raster graphics."+
            "<br>Funding for InscriptiFact RTI viewing functionality was provided by the Institute of Museum<br> " +
            "and Library Services (IMLS)."+
            "<br> InscriptiFact RTI viewing functionality is adapted from technology developed by:" +
            "<ul> <li>Tom Malzbender in association with Daniel Gelb at Hewlett-Packard Labs</li> " +
            "<li>Cultural Heritage Imaging (<a href='http://www.culturalheritageimaging.org/'>http://www.culturalheritageimaging.org</a>)</li>" +
            "<li>Italian National Research Council (CNR),<br>" +
            " Institute of Information Science and Technology (ISTI)," +
            "<br> Visual Computing Laboratory </li>" +
            "<li>University of California Santa Cruz, Computer Science Department, School of Engineering</li>" +
            "<li>University of Minho, School of Engineering, Portugal.</li>" +
            "</ul><br></html>";
}
