package isf.controls.plaf;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicMenuItemUI;

public class InscriptMenuItemUI extends BasicMenuItemUI {

    public static ComponentUI createUI(JComponent jcomponent) {
        InscriptMenuItemUI inscriptmenuui = new InscriptMenuItemUI();
        return inscriptmenuui;
    }

    public InscriptMenuItemUI() {
        super.selectionForeground = new Color(0xac5539);
        super.selectionBackground = new Color(0xd9d9d9);
        super.disabledForeground = new Color(0xa49585);
    }

    public void paint(Graphics g, JComponent jcomponent) {
        if (!jcomponent.isEnabled()) {
            g.setColor(new Color(0xc8c0bc));
            Dimension dimension = jcomponent.getPreferredSize();
            g.fillRect(0, 0, dimension.width, dimension.height);
            g.setColor(super.disabledForeground);
            g.drawString(((JMenuItem) jcomponent).getActionCommand(), 19, 13);
        } else {
            super.paint(g, jcomponent);
        }
    }
}
