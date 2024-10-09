package com.isf.app.view.controls;

import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class InscriptToolButton extends JButton {

   // String userDir;

    public InscriptToolButton(String s) {
      
        
        setBorderPainted(false);
        setIcon(new ImageIcon(getClass().getClassLoader().getResource(s)));
        
        setSelectedIcon(new ImageIcon( getClass().getClassLoader().getResource("/images/" + s.substring(0, s.lastIndexOf('_')) + "_active.jpg")));
        setDisabledIcon(new ImageIcon(getClass().getClassLoader().getResource( "/images/" + s.substring(0, s.lastIndexOf('_')) + "_disabled.jpg")));
        setFocusPainted(false);
    }

    public Dimension getPreferredSize() {
        return new Dimension(getIcon().getIconWidth(), getIcon().getIconHeight());
    }

    public Insets getInsets() {
        return new Insets(0, 0, 0, 0);
    }
}
