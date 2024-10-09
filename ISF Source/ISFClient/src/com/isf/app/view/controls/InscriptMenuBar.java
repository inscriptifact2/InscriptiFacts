package com.isf.app.view.controls;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;

public class InscriptMenuBar extends JMenuBar {

    private ImageIcon im;

    public InscriptMenuBar() {
        im = null;
    }

    public InscriptMenuBar(String s) {
        im = null;
        setBorderPainted(false);
        im = isf.controls.utils.ResolutionManager.getInstance().getImageIcon(s);
    }

    public void paint(Graphics g) {
        if (im != null) {
            g.drawImage(im.getImage(), 0, 0, this);
            setOpaque(false);
        }
        super.paint(g);
    }

    public Dimension getPreferredSize() {
        if (im != null) {
            return new Dimension(im.getIconWidth(), im.getIconHeight());
        } else {
            return super.getPreferredSize();
        }
    }

    public Insets getInsets() {
        return new Insets(0, 0, 0, 0);
    }

    public int getHeight() {
        if (im != null)
            return im.getIconHeight();
        else
            return (int) getPreferredSize().getHeight();
    }
}
