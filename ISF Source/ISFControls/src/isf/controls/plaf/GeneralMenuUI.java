package isf.controls.plaf;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicMenuUI;

public class GeneralMenuUI extends BasicMenuUI {

    public static ComponentUI createUI(JComponent jcomponent) {
        GeneralMenuUI inscriptmenuui = new GeneralMenuUI();
        return inscriptmenuui;
    }

    public GeneralMenuUI() {
        super.selectionForeground = new Color(0xAC5539);
        super.selectionBackground = new Color(0xD9D9D9);
        super.disabledForeground = new Color(0xa49585);

    }

    public void paint(Graphics g, JComponent jcomponent) {
        g.setColor(new Color(0xc8c0bc));
        Dimension dimension = jcomponent.getPreferredSize();
        g.fillRect(1, 0, dimension.width - 2, dimension.height - 1);
        if (((JMenuItem) jcomponent).getIcon() == null) {
            if (!jcomponent.isEnabled()) {

                g.setColor(super.disabledForeground);

            } else if (((JMenuItem) jcomponent).isSelected()) {
                g.setColor(super.selectionForeground);
            } else
                g.setColor(Color.black);

            g.drawString(((JMenuItem) jcomponent).getActionCommand(), 19, 19);
        } else {
            //super.paint(g, jcomponent);
            g.drawImage(((ImageIcon) (((JMenuItem) jcomponent).getIcon())).getImage(), 0, 0, jcomponent.getWidth() - 1, jcomponent.getHeight() - 1, null);
        }
    }
}
