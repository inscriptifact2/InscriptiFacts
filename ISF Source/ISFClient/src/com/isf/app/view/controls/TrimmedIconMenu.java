package com.isf.app.view.controls;

import isf.controls.view.controls.InscriptBorder;
import java.awt.*;
import javax.swing.ImageIcon;

public class TrimmedIconMenu extends QueryMenu {

    private static ImageIcon img = isf.controls.utils.ResolutionManager.getInstance().getScaledWidthIcon("images/QM_background.jpg");

    public TrimmedIconMenu(String s) {
        super("");
        //  UIManager.put("MenuItemUI", "InscriptMenuItemUI");
        setActionCommand(s);
        setContentAreaFilled(false);
        setOpaque(false);
        setBorder(new InscriptBorder(new Color(0xffffff)));
        int i = isf.controls.utils.ResolutionManager.getInstance().getFontSize(14);
        setFont(new Font("Arial", 1, i));
        //todo  QueryMenu Changes
        //setPopupMenuVisible(false);

    }

    public void paint(Graphics g) {
        g.drawImage(img.getImage(), 0, 0, this);
        Dimension dimension = getPreferredSize();
        String s = getActionCommand();
        FontMetrics fontmetrics = g.getFontMetrics();
        int i = fontmetrics.stringWidth(s);
        int j = fontmetrics.getHeight();
        g.drawString(s, dimension.width - i - 25, (dimension.height - j) / 2 + fontmetrics.getAscent());
        super.paint(g);
    }

    public Dimension getPreferredSize() {
        return new Dimension(img.getIconWidth(), img.getIconHeight());
    }

    public Insets getInsets() {
        return new Insets(0, 0, 0, 5);
    }

}
