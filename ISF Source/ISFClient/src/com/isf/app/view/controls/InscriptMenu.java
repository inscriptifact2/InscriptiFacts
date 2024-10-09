package com.isf.app.view.controls;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class InscriptMenu extends JMenu {

    public InscriptMenu(String s) {
        super(s);
        setActionCommand(s);
        setFont(new Font("Arial", 1, 12));
        getPopupMenu().setPopupSize(100, 50);
    }

    public Point getPopupMenuOrigin() {
        Point point = super.getPopupMenuOrigin();
        Dimension dimension = super.getPreferredSize();
        point.x += dimension.getWidth();
        point.y -= dimension.getHeight();
        return point;
    }

    public JMenuItem add(JMenuItem jmenuitem) {
        return super.add(jmenuitem);
    }
}
