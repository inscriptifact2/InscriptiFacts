package com.isf.app.utils;

import com.isf.app.view.containers.TextDivisionPanel;
import com.isf.app.view.gui.ObjectCellRenderer;

public class ClickThread extends Thread {

    ObjectCellRenderer or;

    public ClickThread(ObjectCellRenderer objectrenderer) {
        or = objectrenderer;
        start();
    }

    public void run() {
        try {
            Thread.sleep(50L);
        } catch (InterruptedException interruptedexception) {
        }
        if (or.inside) {
            if (TextDivisionPanel.current != null) {
                TextDivisionPanel.current.setInside();
                /*  Vector v=TextDivisionPanel.currentVector;
                  ObjectRenderer or1=(ObjectRenderer)v.elementAt(0);
                  ObjectRenderer or2=(ObjectRenderer)v.elementAt(1);
                  ObjectRenderer or3=(ObjectRenderer)v.elementAt(2);
                  or1.setInside();
                  or2.setInside();
                  or3.setInside();  */
            }
            TextDivisionPanel.current = or;
            or.setSubData();
        }
    }
}
