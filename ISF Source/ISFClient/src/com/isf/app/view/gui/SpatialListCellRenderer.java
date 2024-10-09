package com.isf.app.view.gui;


import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class SpatialListCellRenderer extends JPanel
        implements ListCellRenderer {

    public SpatialListCellRenderer() {
        setLayout(new BorderLayout());
    }

    public Component getListCellRendererComponent(JList jlist, Object obj, int i, boolean flag, boolean flag1) {
        com.isf.app.models.SpatialListImageObject spatiallistimageobject = (com.isf.app.models.SpatialListImageObject) obj;
        SpatialListCellRenderer spatiallistcellrenderer = new SpatialListCellRenderer();
        spatiallistcellrenderer.setLayout(new BorderLayout(0, 0));
        JLabel jlabel = new JLabel(spatiallistimageobject.getTextView(), 0) {

            public Dimension getPreferredSize() {
                return new Dimension(135, 18);
            }

        };
        jlabel.setBackground(new Color(0xd9d9d9));
        jlabel.setForeground(Color.black);
        jlabel.setBorder(new LineBorder(Color.white));
        jlabel.setFont(new Font("Trebuchet MS", 1, 10));
        spatiallistcellrenderer.add(jlabel, "North");
        JLabel jlabel1 = new JLabel(spatiallistimageobject.getTextViewImage()) {

            public Insets getInsets() {
                return new Insets(3, 3, 3, 3);
            }

            public Dimension getPreferredSize() {
                return new Dimension(134, 134);
            }

        };
        jlabel1.setBorder(BorderFactory.createEtchedBorder(0));
        if (flag || flag1) {
            jlabel1.setBorder(new LineBorder(new Color(0xac5539), 3));
        } else {
            jlabel1.setBorder(new LineBorder(new Color(0xa7a7a7), 3));
        }
        jlabel1.setBackground(new Color(0xa7a7a7));
        jlabel1.setOpaque(true);
        jlabel.setOpaque(true);
        spatiallistcellrenderer.add(jlabel1, "Center");
        return spatiallistcellrenderer;
    }

    public Dimension getPreferredSize() {
        return new Dimension(134, 160);
    }

    public Insets getInsets() {
        return new Insets(1, 1, 1, 1);
    }
}
