package com.isf.app.view.containers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;
import javax.swing.*;

public class FrameTitlePanel extends JPanel {

    //String userDir;
    public static Color bgColor = new Color(0xd9d9d9);
    private JButton viewName;
    private JButton title;

    public FrameTitlePanel() {
       
        setSize(500, 200);
        setLayout(new BorderLayout());
        viewName = createButton();
        add(viewName, "West");
        title = createButton();
        add(title, "Center");
        JButton jbutton = createButton();
        jbutton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/" + "frame_icon01.jpg")));
        jbutton.setHorizontalTextPosition(2);
        add(jbutton, "East");
        setBorder(BorderFactory.createLineBorder(Color.white));
    }

    private JButton createButton() {
        JButton jbutton = new JButton();
        jbutton.setBackground(bgColor);
        jbutton.setBorderPainted(false);
        jbutton.setFocusPainted(false);
        jbutton.setContentAreaFilled(false);
        return jbutton;
    }

    public Insets getinsets() {
        return new Insets(2, 2, 2, 2);
    }

    public String createText(String s) {
        return "<html><body><font face='Arial' color='#000000' size='13pts'><b>" + s + "</b></font></body></html>";
    }

    public void setViewName(String s) {
        viewName.setText(createText(s));
    }

    public void setTitle(String s) {
        title.setText(createText(s));
    }

  /*  PSVM(String args[]) {
        JFrame jframe = new JFrame();
        jframe.setSize(500, 400);
        FrameTitlePanel frametitlepanel = new FrameTitlePanel();
        jframe.getContentPane().add(frametitlepanel, "North");
        jframe.setVisible(true);
    }*/

}
