package com.isf.app.view.containers;

import com.isf.app.controllers.FocusHandler;
import java.awt.Dimension;
import javax.swing.JPanel;

public class QVGlassPane extends JPanel {

    public QVGlassPane() {
        setOpaque(false);
        setLocation(60, 125);
        setLayout(null);
    }

    public Dimension getPreferredSize() {
        return getRootPane().getSize();
    }

    public void setVisible(boolean flag) {
        if (flag) {
            FocusHandler focushandler = (FocusHandler) getComponent(0);
            focushandler.setFocus();
            if (getParent() != null) {
                getParent().validate();
            }
        }

        super.setVisible(flag);
    }

    public boolean isFocusCycleRoot() {
        return true;
    }
}
