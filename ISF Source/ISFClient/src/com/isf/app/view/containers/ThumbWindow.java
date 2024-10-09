package com.isf.app.view.containers;

import com.isf.app.controllers.ThumbContentManager;
import com.isf.app.models.ThumbData;
import com.isf.app.models.ThumbHeader;
import com.isf.app.view.controls.ThumbButton;
import com.isf.app.view.listeners.QuickViewListener;
import com.isf.app.view.listeners.ThumbListener;
import isf.controls.utils.CursorManager;
import isf.controls.view.containers.TrimmedPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

public class ThumbWindow extends TrimmedPanel
        implements ActionListener {

    private ThumbHeader th;
    private ThumbButton tb;
    private Dimension dim;
    public ThumbWindow left;
    public ThumbWindow right;
    private ThumbData thumbData;
    private QuickViewListener qvl;
    private ThumbListener thumbListener;
    private ThumbContentManager tcm;
    private String type;


    public ThumbWindow(ImageIcon imageicon, String s, String s1, String type) {
        th = new ThumbHeader(s1);
        if (imageicon != null)
            tb = new ThumbButton(imageicon, s, type);
        else
            tb = new ThumbButton(s, type);
        th.addActionListener(this);
        tb.addActionListener(this);
        dim = tb.getPreferredSize();
        dim.height += 41;
        dim.width += 6;
        setLayout(new BorderLayout(0, 0));
        setBackground(Color.black);
        this.type = type;

        add(th, "North");
        add(tb, "Center");

    }

    public ThumbWindow(ThumbData tdata) {
        this(tdata.getIcon(), tdata.getLabel(), tdata.getHeader(), tdata.getType());
        this.thumbData = tdata;
        this.type = tdata.getType();


    }

    public void actionPerformed(ActionEvent actionevent) {
        Object obj = actionevent.getSource();
        if (obj == th.arrow) {
            //System.out.println("--------------quick view selected-----");
            //System.out.println("thumbData from thw---"+thumbData.getIcon());
            if (CursorManager.wait)
                return;
            else {
                if (qvl != null) {

                    qvl.showQuickView(thumbData);
                    clearHeaders();
                    th.setBorder(BorderFactory.createLineBorder(new Color(0xac5539), 4));
                    if (thumbListener != null) {
                        thumbListener.headerSelected(thumbData);
                    }

                }
                return;
            }
        }
        if (obj == th && th.selected) {
            clearHeaders();
            th.setBorder(BorderFactory.createLineBorder(new Color(0xac5539), 4));
            if (thumbListener != null) {
                thumbListener.headerSelected(thumbData);
            }
        }
        if (obj == tb) {
            if (tb.selected) {
                if (!tcm.canSelectMore(type)) {
                    tb.selected = false;
                } else {
                    tb.setBorder(BorderFactory.createLineBorder(new Color(0xac5539), 4));
                    thumbListener.thumbSelected(thumbData);
                }
            } else {
                tb.setBorder(null);
                if (thumbListener != null) {
                    thumbListener.thumbDeSelected(thumbData);
                }
            }
        }
        if (th.selected && tb.selected) {
            th.setBorder(null);
            tb.setBorder(null);
            setBorder(BorderFactory.createLineBorder(new Color(0xac5539), 4));
            return;
        }
        setBorder(null);
        if (th.selected) {
            th.setBorder(BorderFactory.createLineBorder(new Color(0xac5539), 4));
        }
        if (tb.selected) {
            tb.setBorder(BorderFactory.createLineBorder(new Color(0xac5539), 4));
        }
    }

    public void clearHeaderSelection() {
        th.selected = false;
        setBorder(null);
        th.setBorder(null);
        if (tb.selected) {
            tb.setBorder(BorderFactory.createLineBorder(new Color(0xac5539), 4));
        }
    }

    public void clearHeaders() {
        for (ThumbWindow thumbwindow = left; thumbwindow != null; thumbwindow = thumbwindow.left) {
            thumbwindow.clearHeaderSelection();
        }

        for (ThumbWindow thumbwindow1 = right; thumbwindow1 != null; thumbwindow1 = thumbwindow1.right) {
            thumbwindow1.clearHeaderSelection();
        }

    }

    public void setSelectedThumb() {
        tb.selected = true;
        tb.setBorder(BorderFactory.createLineBorder(new Color(0xac5539), 4));
    }

    public Dimension getPreferredSize() {
        return dim;
    }

    public Insets getInsets() {
        return new Insets(3, 3, 3, 3);
    }

    public void addQuickViewListener(QuickViewListener quickviewlistener) {
        qvl = quickviewlistener;
    }

    public void addThumbListener(ThumbListener thumblistener) {
        thumbListener = thumblistener;
        tcm = thumblistener.getThumbContentManager();
    }
/*
    PSVM(String args[]) {
        JFrame jframe = new JFrame();
        jframe.getContentPane().setLayout(new GridLayout(3, 5, 10, 10));
        ThumbWindow thumbwindow = null;
        for (int i = 0; i < 15; i++) {
            ThumbWindow thumbwindow1;
            jframe.getContentPane().add(thumbwindow1 = new ThumbWindow(new ImageIcon("images/sid.jpg"), "ISF 1999-069", "1.20, 1.5-11 1983, sec, color", "SID"));
            if (thumbwindow == null) {
                thumbwindow = thumbwindow1;
            } else {
                thumbwindow.right = thumbwindow1;
                thumbwindow1.left = thumbwindow;
                thumbwindow = thumbwindow1;
            }
        }

        jframe.getContentPane().setBackground(Color.black);
        jframe.setSize(400, 400);
        jframe.setVisible(true);
    }
*/
    public void setIcon(ImageIcon ic) {
        if (ic == null)
            tb.setText("Icon Not Found");
        else
            tb.setIcon(ic);
    }

    public void deSelectThumb() {
        tb.setBorder(null);
        tb.selected = false;
    }
}
