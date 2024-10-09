package com.isf.app.view.controls;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.border.Border;

public class InscriptDyanBorder
        implements Border {

    public Color col;
    private int left;
    private int right;
    private int top;
    private int bottom;
    // static ActionListener al;

    public InscriptDyanBorder(Color color, int i, int j, int k, int l) {
        col = null;
        col = color;
        left = j;
        right = l;
        top = i;
        bottom = k;
    }

    public Insets getBorderInsets(Component component) {
        return new Insets(top, left, bottom, right);
    }

    public boolean isBorderOpaque() {
        return false;
    }

    public void paintBorder(Component component, Graphics g, int i, int j, int k, int l) {
        g.setColor(col);
        if (left != 0) {
            g.drawLine(i, j, i, (j + l) - 1);
        }
        if (right != 0) {
            //System.out.println("DRawLineAtRight");
            g.drawLine((i + k) - 1, j, (i + k) - 1, (j + l) - 1);
        }
        if (bottom != 0) {
            g.drawLine(i, (j + l) - 1, (i + k) - 1, (j + l) - 1);
        }
        if (top != 0) {
            g.drawLine(i, j, (i + k) - 1, j);
        }
    }

}
