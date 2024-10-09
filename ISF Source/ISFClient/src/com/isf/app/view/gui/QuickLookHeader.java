package com.isf.app.view.gui;

import isf.controls.view.containers.TrimmedPanel;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JTextArea;

public class QuickLookHeader extends TrimmedPanel {

    JTextArea hb;

    public QuickLookHeader(String s) {
        setLayout(new BorderLayout(0, 0));
        hb = new JTextArea(s);
        hb.setLineWrap(true);
        hb.setWrapStyleWord(true);
        hb.setBackground(getBackground());
        hb.setSelectionColor(getBackground());
        hb.setEditable(false);
        hb.setFont(new Font("Arial", 1, 17));
        add(hb, "Center");
    }

    public void addMouseListener(MouseListener mouselistener) {
        hb.addMouseListener(mouselistener);
    }

    public void addMouseMotionListener(MouseMotionListener mousemotionlistener) {
        hb.addMouseMotionListener(mousemotionlistener);
    }

    public Insets getInsets() {
        return new Insets(4, 4, 4, 4);
    }

    public void setTitle(String s) {
        hb.setText(s);
    }
/*
    PSVM(String args[]) {
        JFrame jframe = new JFrame();
        jframe.getContentPane().setLayout(new FlowLayout());
        jframe.getContentPane().add(new QuickLookHeader("1.20, 1.5-11 1983, sec, color"));
        jframe.getContentPane().setBackground(Color.black);
        jframe.setSize(400, 400);
        jframe.setVisible(true);
    }
    */
}
