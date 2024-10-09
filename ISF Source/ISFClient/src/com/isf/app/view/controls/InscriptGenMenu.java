package com.isf.app.view.controls;

import isf.controls.view.controls.InscriptBorder;
import java.awt.*;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class InscriptGenMenu extends JMenu //implements MouseListener
{

    public int menuItemHeight;
    private Dimension dim;
    public static boolean isGMBClicked = false;

    public InscriptGenMenu(String s) {
        super(s);
        menuItemHeight = 30;
        //UIManager.put("MenuItemUI", "InscriptMenuItemUI");

        setBorderPainted(false);
        setActionCommand(s);
        setVerticalAlignment(1);
        setVerticalTextPosition(1);
        setBorderPainted(true);
        setBorder(new InscriptBorder(Color.white));
        setFont(new Font("Arial", 1, 11));
        setPopupMenuVisible(false);

        getPopupMenu().setBorderPainted(false);

    }

    public InscriptGenMenu(String s, String s1) {
        super(s);
        menuItemHeight = 30;
        // UIManager.put("MenuItemUI", "InscriptMenuItemUI");

        setBorderPainted(false);
        setActionCommand(s);
        setVerticalAlignment(1);
        setVerticalTextPosition(1);
        setBorderPainted(true);
        setBorder(new InscriptBorder(Color.white));
        setFont(new Font("Arial", 1, 11));
        setPopupMenuVisible(false);

        getPopupMenu().setBorderPainted(false);


        InscriptImageIcon inscriptimageicon = new InscriptImageIcon(isf.controls.utils.ResolutionManager.getInstance().getImageIcon(s1).getImage());
        setIcon(inscriptimageicon);
        dim = new Dimension(inscriptimageicon.getIconWidth(), inscriptimageicon.getIconHeight());
        setMargin(new Insets(0, 0, 0, 0));

    }


    public Dimension getPreferredSize() {
        if (getIcon() != null) {
            return dim;
        }
        int i = getFontMetrics(new Font("Arial", 1, 11)).stringWidth(">  " + getActionCommand());
        if (super.getPreferredSize().width < i + 21) {
            return new Dimension(i + 21, menuItemHeight);
        } else {
            return new Dimension(super.getPreferredSize().width, menuItemHeight);
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        Rectangle r = getBounds();
        if (!isEnabled() && getIcon() == null) {
            g.setColor(new Color(0xc8c0bc));
            // Dimension dimension = getPreferredSize();
            g.fillRect(1, 0, r.width - 2, r.height - 1);
            g.setColor(new Color(0xa49585));
            g.drawString(">  " + getActionCommand(), 13, 19);
        } else if (isEnabled() && getIcon() == null) {
            g.setColor(new Color(0xd9d9d9));
            //Dimension dimension = getPreferredSize();
            g.fillRect(1, 0, r.width - 2, r.height - 1);
            g.setColor(new Color(0xac5539));
            g.drawString(">  " + getActionCommand(), 13, 19);
        }
    }


    public Insets getInsets() {
        return new Insets(0, 0, 0, 0);
    }

    public JMenuItem add(JMenuItem jmenuitem) {
        jmenuitem.setPreferredSize(new Dimension((int) super.getPreferredSize().getWidth(), menuItemHeight));
        return super.add(jmenuitem);
    }

    public void setEnabled(boolean enable) {
        super.setEnabled(enable);
        if (enable) {
            setBackground(new Color(0xd9d9d9));
            setForeground(new Color(0xac5539));
        } else {
            setBackground(new Color(0xc8c0bc));
            setForeground(new Color(0xa49585));
        }

    }


}
