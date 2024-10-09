package isf.controls.plaf;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalScrollBarUI;

public class InscriptScrollBarUI extends MetalScrollBarUI {

    public InscriptScrollBarUI() {
    }

    public static ComponentUI createUI(JComponent jcomponent) {
        return new InscriptScrollBarUI();
    }

    protected void paintThumb(Graphics g, JComponent jcomponent, Rectangle rectangle) {
        g.translate(rectangle.x, rectangle.y);
        if (super.scrollbar.getOrientation() == 1) {
            if (!super.isFreeStanding) {
                rectangle.width += 2;
            }
            g.setColor(new Color(0xa7a7a7));
            g.fillRect(0, 0, rectangle.width - 2, rectangle.height - 1);
            g.setColor(Color.white);
            g.drawRect(0, 0, rectangle.width - 2, rectangle.height - 1);
            if (!super.isFreeStanding) {
                rectangle.width -= 2;
            }
        } else {
            if (!super.isFreeStanding) {
                rectangle.height += 2;
            }
            g.setColor(new Color(0xa7a7a7));
            g.fillRect(0, 0, rectangle.width - 1, rectangle.height - 2);
            if (!super.isFreeStanding) {
                rectangle.height -= 2;
            }
        }
        g.translate(-rectangle.x, -rectangle.y);
    }
}
