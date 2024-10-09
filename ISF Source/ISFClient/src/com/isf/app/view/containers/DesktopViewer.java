package com.isf.app.view.containers;

import isf.common.Debug;
import isf.common.utils.LogManager;
import isf.controls.controllers.ActiveXListenerManager;
import isf.controls.controllers.ImageLoaderManager;
import isf.controls.controllers.IsfSplitManager;
import isf.controls.models.DesktopDataObject;
import isf.controls.utils.CursorManager;
import isf.controls.view.containers.ISFSplitPane;
import isf.controls.view.containers.ViewDesktop;
import isf.controls.view.controls.SidDesktop;
import isf.controls.view.listeners.ImageLoaderListener;
import isf.controls.view.listeners.KeyHandler;
import isf.controls.view.listeners.ViewDesktopListener;
import isf.ptm.formats.PTM;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.apache.log4j.Logger;

public class DesktopViewer extends JPanel implements ImageLoaderListener {

    public ViewDesktop vd;
    //public ActivexPanel activexPanel;
    public SidDesktop activexPanel;
    public ISFSplitPane jp;
    /*  private String fileName;
      private JPanel panel;*/
    private Frame parent;
    private int index;
    Logger logger = LogManager.getLogger(com.isf.app.view.containers.DesktopViewer.class);

    public DesktopViewer(Frame frame) {
        ImageLoaderManager.getInstance().registerListener(this);

        parent = frame;
        index = 1;
        setLayout(new BorderLayout(0, 0));
        vd = new ViewDesktop(frame);
        activexPanel = new SidDesktop();
        activexPanel.reset();
        vd.addViewDesktopListener(activexPanel);
        Dimension dimension = vd.getCatButtonSize();

        jp = new ISFSplitPane(1, false, vd, activexPanel, dimension.width, 10);
        java.awt.Rectangle rectangle = activexPanel.getBounds();
        activexPanel.resize(rectangle);
        jp.setDivider(10);
        jp.setOneTouchExpandable(true);
        
        IsfSplitManager.getInstance().setSplitPane(jp, 1);
        add(jp, "Center");
        validate();
        revalidate();
        repaint();
    }

    public void addDesktopData(DesktopDataObject desktopdataobject) {

        vd.addDataObject(desktopdataobject);
    }

    public void addDummy(String type) {
        vd.addDummy(type);
    }


    public Dimension getPreferredSize() {
        return new Dimension(800, 800);
    }

    public void doResize() {
        Debug.debug("Doing Resize");
        activexPanel.setSize(800, 400);
    }

    public void putImage(DesktopDataObject desktopdataobject) {


        jp.setOneSize(vd.getCatButtonSize().width);
        if (desktopdataobject.isAdded()) {
            logger.debug("desktopdataobject.getFileName()   "+desktopdataobject.getFileName());
            if (activexPanel.getActive().equals(desktopdataobject.getFileName()))
                JOptionPane.showMessageDialog(this, "Image is already shown in the control", "Selection Error", 0);
            else
                activexPanel.setActive(desktopdataobject.getFileName());
            return;
        }
        ViewDesktopListener viewdesktoplistener = vd.getVDL();
        if (viewdesktoplistener != null) {
            String s = viewdesktoplistener.addImage(desktopdataobject); //, index + ""
            Debug.debug(s);
            if (s.equalsIgnoreCase("ERROR")) {
                JOptionPane.showMessageDialog(this, " Maximum 5 images can be opened in viewer.", "Too many Images", 0);
                ActiveXListenerManager.deleted(desktopdataobject.getDisplayName());
            } else if (s.equalsIgnoreCase("SUCCESS")) {
                desktopdataobject.setAdded(true);
                index++;
            }
        }

        //System.out.println("DESKTOPLISTENER IS NULL");

        validate();
        revalidate();
        repaint();
    }

    public void setSelectedData(Vector vector) {
        vd.setSelectedData(vector);
    }

    public void resetCursor() {
        //  parent.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        CursorManager.changeCursor(0);

    }

    public void reset() {
        vd.reset();
        activexPanel.reset();
    }

/*
    PSVM(String args[]) {


        JFrame jframe = new JFrame();
        Container container = jframe.getContentPane();
        DesktopViewer dv = new DesktopViewer(jframe);

        container.add(dv);


        jframe.setSize(600, 600);
        jframe.setVisible(true);

        DesktopDataObject ddo = new DesktopDataObject("a", "b", "1K16P26c.sid", "d");
        dv.addDesktopData(ddo);
        dv.putImage(ddo);
        ddo = new DesktopDataObject("a1", "b1", "MM_JP02096_R_Tc.sid", "d1");
        dv.addDesktopData(ddo);
        dv.putImage(ddo);


    }  */

    public KeyHandler getKeyHandler() {
        return activexPanel.sidtoolbar;
    }

    public void putImage(DesktopDataObject desktopdataobject, PTM ptm) {
        ViewDesktopListener viewdesktoplistener = vd.getVDL();
        if (viewdesktoplistener != null) {
            String s = viewdesktoplistener.addImage(desktopdataobject,  ptm);//index + "",
            //System.out.println(s);
            if (s.equalsIgnoreCase("ERROR")) {
                JOptionPane.showMessageDialog(this, " Maximum 5 images can be opened in viewer.", "Too Many Images", 0);
                ActiveXListenerManager.deleted(desktopdataobject.getDisplayName());
            } else if (s.equalsIgnoreCase("SUCCESS")) {
                desktopdataobject.setAdded(true);
                index++;
            }
        }
    }


}
