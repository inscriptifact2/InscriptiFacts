package com.isf.app.models;

import com.isf.app.view.controls.TrimmedIconButton;
import isf.controls.utils.ResolutionManager;
import isf.controls.view.containers.TrimmedPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;

public class ThumbHeader extends TrimmedPanel
        implements MouseListener {

    public TrimmedIconButton arrow;
    public Dimension dim;
    private ActionListener al;
    public boolean selected;

    public ThumbHeader(String s) {
        selected = false;
        setLayout(new BorderLayout(0, 0));
        JTextArea jtextarea = new JTextArea(s);
        jtextarea.setLineWrap(true);
        jtextarea.setWrapStyleWord(true);
        jtextarea.setBackground(getBackground());
        jtextarea.setSelectionColor(getBackground());
        jtextarea.setEditable(false);
        jtextarea.setFont(new Font("Arial", 1, 11));
        jtextarea.addMouseListener(this);
        ImageIcon imageicon;
        arrow = new TrimmedIconButton(imageicon = ResolutionManager.getInstance().getImageIcon("images/arrowup.jpg"));
        dim = new Dimension(imageicon.getIconWidth() + 8, imageicon.getIconHeight() + 8);
        TrimmedPanel trimmedpanel = new TrimmedPanel();
        trimmedpanel.setLayout(new FlowLayout(0, 0, 0));
        trimmedpanel.add(arrow);
        add(jtextarea, "Center");
        add(trimmedpanel, "East");
    }

    public void addActionListener(ActionListener actionlistener) {
        al = actionlistener;
        arrow.addActionListener(actionlistener);
    }

    public void mouseEntered(MouseEvent mouseevent) {
    }

    public void mouseExited(MouseEvent mouseevent) {
    }

    public void mousePressed(MouseEvent mouseevent) {
    }

    public void mouseReleased(MouseEvent mouseevent) {
    }

    public void mouseClicked(MouseEvent mouseevent) {
        selected = true;
        if (al != null) {
            al.actionPerformed(new ActionEvent(this, 1, ""));
        }
    }

    public Dimension getPreferredSize() {
        return dim;
    }

    public Insets getInsets() {
        return new Insets(4, 4, 4, 4);
    }


}
