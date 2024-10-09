package com.isf.app.view.controls;

import com.isf.app.controllers.MenuControlManager;
import isf.controls.view.controls.InscriptBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JMenuItem;

public class QueryMenuItem extends JMenuItem
        implements MouseListener {

    private static Color fg;
    private static Color bg;
    private Color pbg;
    private Color pfg;
    private boolean flag;
    private Object object;
    private Font font;

    public QueryMenuItem() {
        flag = false;
        font = new Font("Arial", 1, 12);
        setFont(font);
    }

    public QueryMenuItem(String s) {
        super(s);
        flag = false;
        // UIManager.put("MenuItemUI", "InscriptMenuItemUI");
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorder(new InscriptBorder(new Color(0xa7a7a7)));
        font = new Font("Arial", 1, 12);
        setFont(font);
        addMouseListener(this);
    }

    public QueryMenuItem(Object obj) {
        this(obj.toString());
        font = new Font("Arial", 1, 12);
        setFont(font);
        object = obj;
    }

    public QueryMenuItem(String s, Color color, Color color1, Font font) {
        super(s);
        flag = false;
        // UIManager.put("MenuItemUI", "InscriptMenuItemUI");
        bg = color;
        fg = color1;
        setContentAreaFilled(false);
        setFocusPainted(false);
        this.font = font;

        setFont(font);
        setBackground(color);
        setForeground(color1);
    }

    public QueryMenuItem(Object obj, Color color, Color color1, Font font) {
        this(obj.toString(), color, color1, font);
        object = obj;
        this.font = font;
        setFont(font);
    }

    public void setSelected(boolean flag1) {
        flag = flag1;
        if (flag) {
            setBackground(pbg = new Color(0xd9d9d9));
            setForeground(pfg = new Color(0xac5539));
        } else {
            setBackground(pbg = bg);
            setForeground(pfg = fg);
        }
    }

    public boolean isSelected() {
        return flag;
    }

    public Dimension getPreferredSize() {
        Dimension dimension = super.getPreferredSize();
        dimension.height = 29;// 29;
        return dimension;
    }

    public void mouseEntered(MouseEvent mouseevent) {
        QueryMenuItem querymenuitem = (QueryMenuItem) mouseevent.getSource();
        pfg = querymenuitem.getForeground();
        pbg = querymenuitem.getBackground();
        querymenuitem.setBackground(new Color(0xd9d9d9));
        querymenuitem.setForeground(new Color(0xac5539));
        MenuControlManager.setMenuHasControl(true);
    }

    public void mouseExited(MouseEvent mouseevent) {
        QueryMenuItem querymenuitem = (QueryMenuItem) mouseevent.getSource();
        querymenuitem.setBackground(pbg);
        querymenuitem.setForeground(pfg);
        MenuControlManager.setMenuHasControl(false);
    }

    public void mouseClicked(MouseEvent mouseevent) {
    }

    public void mousePressed(MouseEvent mouseevent) {
    }

    public void mouseReleased(MouseEvent mouseevent) {
    }

    public boolean isLeaf() {
        return true;
    }

    public String toString() {
        // return "<html><body><font size='11pt' face='arial'><b>" + super.toString() + "<b></font><body></html>";
        return super.toString();
    }

    static {
        fg = Color.black;
        bg = Color.white;
    }
}
