package com.isf.app.view.controls;

import com.isf.app.utils.GeneralMenuManager;
import isf.controls.plaf.GeneralMenuItemUI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JMenuItem;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Dec 21, 2007
 * Time: 3:23:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class GeneralMenuItem extends JMenuItem implements MouseListener {


    int menuItemHeight;

    public GeneralMenuItem(String s) {
        super(s);
        menuItemHeight = 28;
        setBackground(new Color(0xc8c0bc));

        setForeground(new Color(0));
        setActionCommand(s);
        setFocusPainted(false);
        setBorderPainted(true);
        // setContentAreaFilled(false);
        //  setOpaque(true);
        //  setHorizontalAlignment(2);
        //  setVerticalTextPosition(0);
        setFont(new Font("Arial", 1, 11));
        setMargin(new Insets(0, 0, 0, 0));
        setBorder(new InscriptDyanBorder(Color.white, 1, 1, 1, 1));
        addMouseListener(this);
        setUI(new GeneralMenuItemUI());

    }

    public Dimension getPreferredSize() {


        int i = getFontMetrics(new Font("Arial", 1, 11)).stringWidth(">  " + getActionCommand());
        if (super.getPreferredSize().width < i + 21) {
            //System.out.println("Now preferredsize less WinMenu is : "+menuItemHeight );
            return new Dimension(i + 21, menuItemHeight);
        } else {
            //System.out.println("Now preferredsize >= WinMenu is : "+menuItemHeight );
            return new Dimension(super.getPreferredSize().width, menuItemHeight);
        }
    }


    public int getHeight() {
        return menuItemHeight;
    }

    public Insets getInsets() {
        return new Insets(0, 0, 0, 0);
    }

    public void mouseClicked(MouseEvent event) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mousePressed(MouseEvent event) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseReleased(MouseEvent event) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseEntered(MouseEvent event) {
        //To change body of implemented methods use File | Settings | File Templates.
        GeneralMenuManager.performExit(null, false);
        setSelected(true);
    }

    public void mouseExited(MouseEvent event) {
        //To change body of implemented methods use File | Settings | File Templates.
        setSelected(false);
        GeneralMenuManager.performExit((JMenuItem) event.getSource(), true);
    }
}
