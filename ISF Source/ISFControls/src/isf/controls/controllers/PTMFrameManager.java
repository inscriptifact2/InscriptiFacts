/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.controls.controllers;

import isf.common.utils.LogManager;
import isf.controls.utils.GlassPaneManager;
import isf.controls.view.containers.PTMImpl;
import isf.controls.view.containers.PTMPanel;
import isf.controls.view.containers.PTMPanelImpl;
import isf.controls.view.controls.ISFButton;
import isf.controls.view.listeners.PTMFrameChangeListener;
import isf.ptm.controls.PTMWindow;
import java.awt.Component;
import java.util.*;

import javax.swing.JInternalFrame;
import org.apache.log4j.Logger;

/**
 * @author IN030223
 */
public class PTMFrameManager {

    static Logger log = LogManager.getLogger(isf.controls.controllers.PTMFrameManager.class);

    /**
     * @return the autoClick
     */
    public static boolean isAutoClick() {
        return autoClick;
    }

    /**
     * @param aAutoClick the autoClick to set
     */
    public static void setAutoClick(boolean aAutoClick) {
        autoClick = aAutoClick;
    }
    private PTMWindow panel;
    private ISFButton btn;
    private static PTMFrameManager manager = null;
    private HashMap pfcl = new HashMap();
    //  private int count = 0;
    private static double version;
    private static int mode = 0;
    private static boolean autoClick = false;
    private HashMap copies = new HashMap();

    static {
        String versionstr = System.getProperty("java.vm.version");
        if (versionstr != null) {
            versionstr = versionstr.substring(0, 3);
            version = Double.parseDouble(versionstr);
        } else {
            version = 1.3;
        }
    }

    public void registerChangeListener(PTMFrameChangeListener cl, PTMWindow window) {
        ArrayList list = new ArrayList();
        if (pfcl.containsKey(window)) {
            list = (ArrayList) pfcl.get(window);
        }

        if (!list.contains(cl)) {
            list.add(cl);
        }
        pfcl.put(window, list);
    }

    public void releaseChangeListeners() {
        pfcl = new HashMap();
    }

    public void releaseChangeListener(PTMFrameChangeListener l) {

        Set keys = pfcl.keySet();
        Iterator i = keys.iterator();
        ArrayList list = new ArrayList();
        while (i.hasNext()) {
            PTMWindow p = (PTMWindow) i.next();
            list = (ArrayList) pfcl.get(p);

            if (list.contains(l)) {
                list.remove(l);
                pfcl.put(p, list);
                break;
            }
        }
    }

    public void releaseChangeListener(PTMWindow window) {


        if (pfcl.containsKey(window)) {
            pfcl.remove(window);
        }


    }

    public void releaseChangeListener(PTMFrameChangeListener l, PTMWindow window) {

        ArrayList list = new ArrayList();
        if (pfcl.containsKey(window)) {
            list = (ArrayList) pfcl.get(window);
        }

        if (list.contains(l)) {
            list.remove(l);
        }
        pfcl.put(window, list);
    }

    public void refreshControls() {
        Set keys = pfcl.keySet();
        Iterator i = keys.iterator();

        while (i.hasNext()) {
            PTMWindow p = (PTMWindow) i.next();
            ArrayList list = (ArrayList) pfcl.get(p);
            for (int j = 0; j < list.size(); j++) {
                PTMFrameChangeListener listener = (PTMFrameChangeListener) list.get(j);
                listener.resetControls();
            }
        }
    }

    public void setPTMWindow(PTMWindow pane) {
        Component comp = null;
        if (GlassPaneManager.getInstance().isShowing()) {
            comp = GlassPaneManager.getInstance().getComponent();
        }
        if (panel != null) {
            panel.setFrameActive(false);
        }
        panel = pane;
        if (panel != null) {
            panel.setFrameActive(true);
        }

        if (comp != null && comp.getClass().getName().equalsIgnoreCase("isf.view.ControlsPopUp")) {

            if (btn != null) {
                PTMFrameManager.setAutoClick(true);
                btn.doClick();
                releaseChangeListeners();
                btn.doClick();
                PTMFrameManager.setAutoClick(false);
            }
        } else {
            refreshControls();
        }


    }

    public void showControls() {
        if (btn != null) {
            btn.doClick();
        }
    }

    public void registerButton(ISFButton btn) {
        this.btn = btn;
    }

    public void addCopies(String name, JInternalFrame frame) {
        Vector v = new Vector();
        if (copies.containsKey(name)) {
            v = (Vector) copies.get(name);
            v.add(frame);

        } else {
            v.add(frame);
        }
        copies.put(name, v);
    }

    public Vector getCopies(String name) {
        Vector v = new Vector();
        if (copies.containsKey(name)) {
            v = (Vector) copies.get(name);
        }
        return v;
    }

    public void removeCopies(String name) {
        if (copies.containsKey(name)) {
            copies.remove(name);
        }
    }

    public PTMWindow getPTMWindow() {
        return panel;
    }

    private PTMFrameManager() {
    }

    public static PTMFrameManager getInstance() {
        if (manager == null) {
            manager = new PTMFrameManager();
        }
        return manager;
    }

    public static PTMPanel getNewPTMPanel() {
        PTMPanel panel = null;

        if (version >= 1.5) {
            panel = (PTMPanel) new PTMPanelImpl();
        } else {
            panel = new PTMImpl();
        }
        return panel;

    }

    public static double getVersion() {
        return version;
    }
    boolean diffuse;
    boolean canChange = true;

    public void setDiffuseOperation(boolean flag, boolean fromFramework) {
        if (fromFramework) {
            canChange = false;
            diffuse = flag;
        } else if (canChange) {
            diffuse = flag;
        }

    }

    public boolean getDiffuseOperation() {
        return diffuse;
    }
    boolean light;

    public void setLightOperation(boolean flag) {
        light = flag;
    }

    public boolean getLightOperation() {
        return light;
    }

    public static ISFButton[] getPTMButtons() {
        ISFButton[] buttons = null;

        if (version >= 1.5) {
            try {
                Class c = Class.forName("isf.view.PTMTools");
                buttons = (ISFButton[]) c.getMethod("getPTMButtons", null).invoke(null, null);
            } catch (Exception cnfe) {
                log.error(cnfe);
            }
        }

        return buttons;
    }

    /**
     * @return the mode
     */
    public static int getMode() {
        return mode;
    }

    /**
     * @param mode the mode to set
     */
    public static void setMode(int mod) {
        mode = mod;
    }
}
