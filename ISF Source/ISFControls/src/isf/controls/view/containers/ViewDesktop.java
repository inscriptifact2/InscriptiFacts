package isf.controls.view.containers;

//import com.isf.common.Debug;

import isf.common.Debug;
import isf.controls.controllers.LoadManager;
import isf.controls.models.DesktopDataObject;
import isf.controls.utils.CursorManager;
import isf.controls.utils.GlassPaneManager;
import isf.common.utils.InscriptConfigLoader;
import isf.controls.view.controls.ISFProgressBar;
import isf.controls.view.controls.ViewDesktopButton;
import isf.controls.view.listeners.ActivationListener;
import isf.controls.view.listeners.ViewDesktopListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class ViewDesktop extends JPanel
        implements ActionListener, ActivationListener, MouseMotionListener, MouseListener {

    private ViewDesktopListener vdListener;
    private ViewDesktopPanels vdtPanels;
    private Container contentPane;
    private Vector v;
    private Frame parent;
    private CatalougeWindow cp;
    //private String mode;
    //    public final GlassPane gp1 = new GlassPane();
    private Rectangle old;
    private Point p, temp;
    int mouseX, mouseY, xposition,yposition;
   // private ISFProgressBar progress;
    
    public ViewDesktop() {
        v = new Vector();
        // cp = null;
        init();
        p = new Point();
    }

    public void setFrame(Frame frame) {

        parent = frame;
    }

    public ViewDesktop(Frame frame) {
        v = new Vector();
        cp = null;
        init();
        p = new Point();
        parent = frame;

    }

    private void init() {
        setLayout(new BorderLayout(0, 0));
        setBackground(Color.black);
        setVisible(true);
        setSize(400, 500);
        vdtPanels = new ViewDesktopPanels();
        vdtPanels.addActionListener(this);
        vdtPanels.addActivationListener(this);
        
        add(vdtPanels.catalogPanel, "North");
        add(vdtPanels, "West");

        String appName = InscriptConfigLoader.getInstance().getProperty("appName");
       //   if(appName!=null && appName.equalsIgnoreCase("Inscriptifact")){
       /*progress = new ISFProgressBar(Color.green);
        LoadManager.getInstance().registerMonitor(progress);
        progress.setBackgroundColor(Color.black);
        progress.setForegroundColor(Color.white);*/
       // progress = new JProgressBar();
        //progress.setStringPainted(true);
        //progress.setFont(new Font("Arial",Font.ITALIC,12));
        //LoadManager.getInstance().registerMonitor(progress);
       // progress.setBackground(Color.black);
       // progress.setForeground(Color.white);

       // add(progress,"South");
     //    } 
        


    }

    public void addDataObject(DesktopDataObject desktopdataobject) {
        vdtPanels.addDataObject(desktopdataobject);
    }

    public void addDummy(String type) {
        vdtPanels.addDummy(type);
    }


    public void addViewDesktopListener(ViewDesktopListener viewdesktoplistener) {
        vdListener = viewdesktoplistener;
    }

    public void actionPerformed(ActionEvent actionevent) {
        String s = actionevent.getActionCommand();
        v = vdtPanels.getDataList();
        // GlassPaneManager.getInstance().setUpGlasssPane(getRootPane());
        Debug.debug(s);
        //        GlassPaneManager.getInstance().setUpGlasssPane(getRootPane() );
        /*if (cp != null && cp.isShowing()) {
           // cp.dispose();
            GlassPaneManager.getInstance().hideAll();
        }*/
        if (s.equals("Cataloging")) {
            vdtPanels.catButton.setSelected(true);
            vdtPanels.dublincatButton.setSelected(false);
            cp = new CatalougeWindow();
            //////////////
            cp.addMouseListener(this);
            cp.addMouseMotionListener(this);
            //   gp1.add(cp);
            //////////////////
            cp.addCWListener(vdtPanels);
            cp.setData(vdtPanels.getActiveImageData());
            cp.showCatalougeData();
            vdtPanels.mode = "Cataloging";
            //  cp.show();
            // cp.toFront();
            GlassPaneManager gp1 = GlassPaneManager.getInstance();
            if (!gp1.isShowing() || old == null) {
                GlassPaneManager.getInstance().showComponent(cp, new Rectangle(350, 200, cp.getWidth(), cp.getHeight()));
            } else if (old != null) {
                //   GlassPaneManager.getInstance().refreshGlassPane() ;
                GlassPaneManager.getInstance().showComponent(cp, old);
            }


            /*if(old!=null)
                         GlassPaneManager.getInstance().showComponent(cp,old)  ;
                        else
                            GlassPaneManager.getInstance().showComponent(cp,new Rectangle(350,200,cp.getWidth(),cp.getHeight()))  ;
            */
            return;

        }
        if (s.equals("DublinCoreCataloging")) {
            vdtPanels.catButton.setSelected(false);
            vdtPanels.dublincatButton.setSelected(true);
            cp = new CatalougeWindow();
            cp.addCWListener(vdtPanels);
            cp.addMouseListener(this);
            cp.addMouseMotionListener(this);
            cp.setData(vdtPanels.getActiveImageData());
            cp.showDublinData();
            vdtPanels.mode = "DublinCoreCataloging";
            // cp.show();
            // cp.toFront();
            /*  if(old!=null)
                GlassPaneManager.getInstance().showComponent(cp,old)  ;
              else
                  GlassPaneManager.getInstance().showComponent(cp,new Rectangle(350,200,cp.getWidth(),cp.getHeight()))  ;
  */

            GlassPaneManager gp1 = GlassPaneManager.getInstance();
            if (!gp1.isShowing() || old == null) {
                GlassPaneManager.getInstance().showComponent(cp, new Rectangle(350, 200, cp.getWidth(), cp.getHeight()));
            } else if (old != null) {
                //  GlassPaneManager.getInstance().refreshGlassPane() ;
                GlassPaneManager.getInstance().showComponent(cp, old);
            }
            return;
        }
        /* if (cp != null && cp.isShowing()) {
           //  cp.dispose();
             GlassPaneManager.getInstance().hideAll();

         }*/
        ViewDesktopButton viewdesktopbutton = (ViewDesktopButton) actionevent.getSource();
        DesktopDataObject desktopdataobject = viewdesktopbutton.getObject();
        try {
            if (vdListener != null) {
                String s1 = vdListener.checkImage(desktopdataobject);//,s
            }
        } catch (Exception exception) {
        }
    }

    public Insets getInsets() {
        return new Insets(0, 0, 0, 0);
    }

    public ViewDesktopListener getVDL() {
        return vdListener;
    }

    public Dimension getCatButtonSize() {  //checkwhy
        if (vdtPanels != null && vdtPanels.catButton != null)
            return vdtPanels.catButton.getSize();
        else
            return new Dimension(100, 20);
    }

    public boolean getNotification() {
        return vdtPanels.getNotification();
    }

    public void setSelectedData(Vector vector) {
        vdtPanels.setSelectedData(vector);

    }

    public void activationChanged() {
        if (cp != null && cp.isShowing()) {
            cp.setData(vdtPanels.getActiveImageData());
            if (vdtPanels.mode.equals("DublinCoreCataloging")) {
                cp.showDublinData();
            } else if (vdtPanels.mode.equals("Cataloging")) {
                cp.showCatalougeData();
            }
        }
    }

    public void mousePressed(MouseEvent mouseevent) {
        //  CatalougeWindow cw = cp;
        temp = mouseevent.getPoint();
        // cw.setCursor(new Cursor(13));
        CursorManager.changeCursor(13);
        cp.cat.setCursor(new Cursor(13));
        GlassPaneManager gp1 = GlassPaneManager.getInstance();

        xposition = cp.getX();
        yposition = cp.getY();
        //System.out.println("xposition  :" + xposition);
        // System.out.println("yposition  :" + yposition);
        Dimension dimension = cp.getSize();
        //System.out.println("size :"+dimension);
        p.x = xposition - mouseX;
        p.y = yposition - mouseY;
        //  Graphics2D graphics2d = (Graphics2D) vdtPanels.getGraphics();
        //   Graphics2D graphics2d = (Graphics2D) GlassPaneManager.getInstance().getGraphics();
        Graphics2D graphics2d = (Graphics2D) gp1.getGraphics();

        old = new Rectangle(p.x, p.y, dimension.width, dimension.height);
        graphics2d.setXORMode(Color.yellow);
        graphics2d.draw(old);
    }

    public void mouseClicked(MouseEvent mouseevent) {
    }

    public void mouseExited(MouseEvent mouseevent) {
    }

    public void mouseEntered(MouseEvent mouseevent) {
    }

    public void mouseReleased(MouseEvent mouseevent) {
        /*CatalougeWindow cw = cp;
        //System.out.println("in muse released");
        Graphics2D graphics2d = (Graphics2D) GlassPaneManager.getInstance().getGraphics();
        graphics2d.setXORMode(Color.yellow);
        graphics2d.draw(old);
        cw.setLocation(old.getLocation());
        old = null;
        cw.setCursor(new Cursor(0));
        cw.cat.setCursor(new Cursor(0));*/


        GlassPaneManager gp1 = GlassPaneManager.getInstance();
        // QuickViewGP quickviewgp = qvgp;
        Graphics2D graphics2d = (Graphics2D) gp1.getGraphics();
        graphics2d.setXORMode(Color.yellow);
        graphics2d.draw(old);
        cp.setLocation(old.getLocation());
        old = null;
        //  quickviewgp.setCursor(new Cursor(0));
        CursorManager.changeCursor(0);
    }

    public void mouseDragged(MouseEvent mouseevent) {
        /* CatalougeWindow cw = cp;
         Dimension dimension = cw.getSize();
         p.x = ((mouseevent.getX() - temp.x) + xposition) - mouseX;
         p.y = ((mouseevent.getY() - temp.y) + yposition) - mouseY;
         Rectangle rectangle = new Rectangle(p.x, p.y, dimension.width, dimension.height);
         //	System.out.println("in muse dragged  " +rectangle);
         Graphics2D graphics2d = (Graphics2D) GlassPaneManager.getInstance().getGraphics();
         graphics2d.setXORMode(Color.yellow);
         graphics2d.draw(old);
         graphics2d.draw(rectangle);
         old = rectangle;*/

        GlassPaneManager gp1 = GlassPaneManager.getInstance();

        // QuickViewGP quickviewgp = qvgp;
        Dimension dimension = cp.getSize();
        p.x = ((mouseevent.getX() - temp.x) + xposition) - mouseX;
        p.y = ((mouseevent.getY() - temp.y) + yposition) - mouseY;
        Rectangle rectangle = new Rectangle(p.x, p.y, dimension.width, dimension.height);
        Graphics2D graphics2d = (Graphics2D) gp1.getGraphics();
        graphics2d.setXORMode(Color.yellow);
        graphics2d.draw(old);
        graphics2d.draw(rectangle);
        old = rectangle;

    }

    public void mouseMoved(MouseEvent mouseevent) {
    }

    public void reset() {
        vdtPanels.reset();
    }


}
