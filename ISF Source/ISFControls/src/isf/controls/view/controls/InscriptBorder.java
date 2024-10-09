package isf.controls.view.controls;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.border.Border;

public class InscriptBorder
        implements Border {

    public Color col;

    public InscriptBorder(Color color) {
        col = null;
        col = color;
    }

    public Insets getBorderInsets(Component component) {
        return new Insets(0, 1, 1, 1);
    }

    public boolean isBorderOpaque() {
        return false;
    }

    public void paintBorder(Component component, Graphics g, int i, int j, int k, int l) {
        g.setColor(col);
        g.drawLine(i, j, i, (j + l) - 1);
        g.drawLine((i + k) - 1, j, (i + k) - 1, (j + l) - 1);
        g.drawLine(i, (j + l) - 1, (i + k) - 1, (j + l) - 1);
    }
}
