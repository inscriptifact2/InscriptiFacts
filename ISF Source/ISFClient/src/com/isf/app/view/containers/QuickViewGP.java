package com.isf.app.view.containers;

import com.isf.app.controllers.FocusHandler;
import com.isf.app.models.ThumbData;
import com.isf.app.view.listeners.ArrowListener;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

public class QuickViewGP extends JPanel
        implements ArrowListener, FocusHandler {

    public QuickViewPanel qvp;
    ThumbData thumbData;
    Container c;
    MouseListener ml;
    MouseMotionListener mml;

    public QuickViewGP() {
        c = this;
        setBounds(0, 0, 300, 150);
        c.setBackground(new Color(0xd9d9d9));
        c.setLayout(new FlowLayout(1));
        // qvp = new QuickViewPanel();
        // c.add(qvp);
    }

    public void setThumbData(Insets insets, ThumbData thumbdata) {
        thumbData = thumbdata;
        if (qvp != null) {
            c.remove(qvp);
            qvp = null;
            qvp = new QuickViewPanel(insets, thumbdata);
            qvp.addMouseMotionListener(mml);
            qvp.addMouseListener(ml);
        } else {
            qvp = new QuickViewPanel(insets, thumbdata);
            qvp.addMouseMotionListener(mml);
            qvp.addMouseListener(ml);
        }
        if (qvp != null) {
            //  qvp.addMouseMotionListener(mml);
            //  qvp.addMouseListener(ml);
            if (qvp.hasImage)
                c.add(qvp);
        }

    }

    public void addMouseListener(MouseListener mouselistener) {
        super.addMouseListener(mouselistener);
        ml = mouselistener;
    }

    public void addMouseMotionListener(MouseMotionListener mousemotionlistener) {
        super.addMouseMotionListener(mousemotionlistener);
        mml = mousemotionlistener;
    }

    public void addArrowListener(ActionListener actionlistener) {
        if (qvp != null)
            qvp.addActionListener(actionlistener);
    }

    public void setFocus() {
        if (qvp != null)
            qvp.requestFocus();
    }

    public boolean isValidIcon() {
        if (qvp != null)
            return qvp.isValidIcon();
        else
            return false;
    }


}
