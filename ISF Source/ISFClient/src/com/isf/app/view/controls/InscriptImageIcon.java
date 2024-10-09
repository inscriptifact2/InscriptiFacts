package com.isf.app.view.controls;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class InscriptImageIcon extends ImageIcon {

    public InscriptImageIcon(String s) {
        super(s);
    }

    public InscriptImageIcon(Image image) {
        super(image);
    }

    public void paintIcon(Component component, Graphics g, int i, int j) {
        super.paintIcon(component, g, 0, 0);
    }
}
