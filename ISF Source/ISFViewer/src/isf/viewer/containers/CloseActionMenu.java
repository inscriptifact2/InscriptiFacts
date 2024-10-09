/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package isf.viewer.containers;

import isf.controls.controllers.PTMFrameManager;
import isf.controls.utils.GlassPaneManager;
import isf.controls.utils.ResolutionManager;
import isf.controls.view.containers.ControlMenu;
import isf.controls.view.controls.InscriptButton;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.*;

/**
 * @author IN030223
 */
public class CloseActionMenu extends ControlMenu implements ActionListener {


   // private JButton close;
    
     private JLabel title;
    private InscriptButton close1, close2;

    private static ImageIcon titleIcon =ResolutionManager.getInstance().loadImage("images/header.jpg");
    private static ImageIcon closeRight = ResolutionManager.getInstance().loadImage("images/dublincore_right.jpg");
    private static ImageIcon closeLeft = ResolutionManager.getInstance().loadImage("images/dublincore_left.jpg");
  

    public CloseActionMenu() {

        super();
       /* close = new JButton(ResolutionManager.getInstance().loadImage("images/exit.gif")) {
            public Insets getInsets() {
                return new Insets(0, 0, 0, 0);
            }

            public Dimension getPreferredSize() {
                return new Dimension(40, 20);
            }
        };
        this.setLayout(new FlowLayout(FlowLayout.RIGHT));
        close.setContentAreaFilled(false);
        close.setBorderPainted(false);

        setBackground(new Color(0xd9d9d9));
        setForeground(new Color(0xAC5539));
        close.setBackground(new Color(0xd9d9d9));
        close.setForeground(new Color(0xAC5539));
        close.addActionListener(this);
        this.add(close);*/
        
         setSize(titleIcon.getIconWidth() + closeLeft.getIconWidth() + closeRight.getIconWidth() + 8, titleIcon.getIconHeight());
        /* title = new InscriptButton("images/dublincore_blank01.jpg");
         title.setScaledStatus(true);
         title.addActionListener(this);
         title.setBorderPainted(false);
         title.setFocusPainted(false);
         title.setContentAreaFilled(false);*/
        title = new JLabel(titleIcon);

        //  close = new InscriptButton("images/dublic_close.jpg");
        close1 = new InscriptButton("images/dublincore_left.jpg");
        close1.setScaledStatus(true);
        close1.addActionListener(this);
        close1.setBorderPainted(false);
        close1.setFocusPainted(false);
        close1.setContentAreaFilled(false);
        close2 = new InscriptButton("images/dublincore_right.jpg");
        close2.setScaledStatus(true);
        close2.addActionListener(this);
        close2.setBorderPainted(false);
        close2.setFocusPainted(false);
        close2.setContentAreaFilled(false);
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        p.add(close1);
        p.add(title);
        p.add(close2);
        p. setBackground(new Color(0xd9d9d9));
        p.setForeground(new Color(0xAC5539));
        add(p, "Center");
         setBackground(new Color(0xd9d9d9));
        setForeground(new Color(0xAC5539));


    }

    public void init() {

    }

    public void processMouseEvent(MouseEvent event, MenuElement[] path, MenuSelectionManager manager) {

    }

    public void processKeyEvent(KeyEvent event, MenuElement[] path, MenuSelectionManager manager) {

    }

    public void menuSelectionChanged(boolean isIncluded) {

    }

    public MenuElement[] getSubElements() {
        return new MenuElement[0];
    }

    public Component getComponent() {
        return this;
    }

    public Dimension getPreferredSize() {
        return new Dimension(titleIcon.getIconWidth() + closeLeft.getIconWidth() + closeRight.getIconWidth(), titleIcon.getIconHeight()+2);
    }

    public Dimension getMinimumSize() {
        return new Dimension(titleIcon.getIconWidth() + closeLeft.getIconWidth() + closeRight.getIconWidth(), titleIcon.getIconHeight()+2);
    }

    public Dimension getMaximumSize() {
        return new Dimension(titleIcon.getIconWidth() + closeLeft.getIconWidth() + closeRight.getIconWidth(), titleIcon.getIconHeight()+2);
    }

    public void actionPerformed(ActionEvent e) {

        GlassPaneManager.getInstance().hideAll();
	PTMFrameManager.getInstance().releaseChangeListeners();
    }
}