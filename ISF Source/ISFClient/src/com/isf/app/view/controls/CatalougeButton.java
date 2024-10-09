package com.isf.app.view.controls;

import isf.controls.utils.CursorManager;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Icon;
import javax.swing.JButton;

public class CatalougeButton extends JButton implements MouseListener {

    private Object o;

    public CatalougeButton(String s) {
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setMargin(new Insets(0, 0, 0, 0));
        setIcon(isf.controls.utils.ResolutionManager.getInstance().getImageIcon(s));
        setActionCommand(s.substring(0, s.indexOf('.')));
        if (s.endsWith("Go.jpg")) {
            addMouseListener(this);
        }
    }

    public void setDataObject(Object obj) {
        o = obj;
    }

    public Object getDataObject() {
        return o;
    }

    public Insets getInsets() {
        return new Insets(0, 0, 0, 0);
    }

    public Dimension getPreferredSize() {
        Icon icon = getIcon();
        return new Dimension(icon.getIconWidth(), icon.getIconHeight());
    }

    public void mouseClicked(MouseEvent event) {
    }

    public void mousePressed(MouseEvent event) {
    }

    public void mouseReleased(MouseEvent event) {
    }

    public void mouseEntered(MouseEvent event) {
        CursorManager.changeCursor(12);
    }

    public void mouseExited(MouseEvent event) {
        CursorManager.changeCursor(-1);
    }
}
