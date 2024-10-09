package com.isf.app.view.containers;

import isf.controls.utils.ResolutionManager;
import isf.controls.view.controls.InscriptBorder;
import isf.controls.view.controls.InscriptButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

public class ButtonsPanel extends JPanel {

    public InscriptButton begin;
    public InscriptButton newb;

    /* private ActionListener al;
     private MouseListener ml;
 */
    public ButtonsPanel() {
        /*  al = null;
          ml = null;*/
        setBackground(new Color(0xd9d9d9));
        setBorder(new InscriptBorder(Color.white));
        javax.swing.ImageIcon imageicon = ResolutionManager.getInstance().getImageIcon("images/QM_beginsearch_active.gif");
        begin = new InscriptButton(imageicon);
        begin.setActionCommand("Qm_beginsearch_active");
        begin.setBackground(new Color(0xd9d9d9));
        begin.setBorderPainted(false);
        // begin.setMnemonic(KeyEvent.VK_B );
        javax.swing.ImageIcon imageicon1 = ResolutionManager.getInstance().getImageIcon("images/QM_newsearch_active.gif");
        newb = new InscriptButton(imageicon1);
        newb.setActionCommand("Qm_newsearch_active");
        newb.setBorderPainted(false);
        newb.setBackground(new Color(0xd9d9d9));
        newb.setFocusPainted(false);
        // newb.setMnemonic(KeyEvent.VK_N );
        begin.setFocusPainted(false);
        add(begin);
        add(newb);
        setLayout(new FlowLayout(1, 0, 10));
    }

    public void addActionListener(ActionListener actionlistener) {
        // al = actionlistener;
        begin.addActionListener(actionlistener);
        newb.addActionListener(actionlistener);
    }

    public void addMouseListener(MouseListener mouselistener) {
        //ml = mouselistener;
        begin.addMouseListener(mouselistener);
        newb.addMouseListener(mouselistener);
    }

    public Insets getInsets() {
        return new Insets(0, 0, 0, 0);
    }

    public Dimension getPreferredSize() {
        return new Dimension(begin.getIcon().getIconWidth() + newb.getIcon().getIconWidth(), begin.getIcon().getIconHeight() + newb.getIcon().getIconHeight());
    }
}
