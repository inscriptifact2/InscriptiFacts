package com.isf.app.view.controls;

import com.isf.app.controllers.MenuControlManager;
import isf.common.utils.LogManager;
import isf.controls.view.controls.InscriptBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import javax.swing.JMenu;
import org.apache.log4j.Logger;

public class QueryMenu extends JMenu
        implements MouseListener {

    Logger logger = LogManager.getLogger(com.isf.app.view.controls.QueryMenu.class);

    private static Color bg;
    private static Color fg;
    private boolean flag;
    private Object object;
    private Color pbg;
    private Color pfg;
    private Font font;

    private ArrayList list ;
    private boolean isRemoved = false;

    public static boolean isQMClicked = false;

    public QueryMenu() {
        // list = new ArrayList();
        flag = false;
        object = null;
        font = new Font("Arial", 1, 12);
        setFont(font);
        removeListeners();
    }

    private void removeListeners() {

        try {
            String className = "javax.swing.JMenu";
            String methodName = "getMouseListeners";
            Class c = Class.forName(className);
            Method m = c.getMethod(methodName, new Class[]{});
            if (m != null) {
                MouseListener[] ml = (MouseListener[]) m.invoke(this, null);
				if(ml!=null && list!=null){
                for (int i = 0; i < ml.length; i++)
                    list.add(ml[i]);
				}

            }
        } catch (ClassNotFoundException cnfe) {
           logger.error(cnfe);
        } catch (NoSuchMethodException nsme) {
            logger.error(nsme);

        } catch (IllegalAccessException exp) {

           logger.error(exp);
        } catch (InvocationTargetException enp) {
           logger.error(enp);

        } catch (Exception exp) {
            //logger.error(exp.getMessage());
            logger.error(exp);
        }


        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                removeMouseListener((MouseListener) list.get(i));

            }
        }
        isRemoved = true;
        list = null;
    }

    public QueryMenu(String s) {
        super(s);
        //list = new ArrayList();
        flag = false;
        object = null;
        //  UIManager.put("MenuItemUI", "InscriptMenuItemUI");
        font = new Font("Arial", 1, 12);
        setFont(font);
        setBorder(new InscriptBorder(new Color(0xa7a7a7)));
        setContentAreaFilled(false);
        setFocusPainted(false);
        removeListeners();
        //setPopupMenuVisible(false);
        addMouseListener(this);

    }

    public QueryMenu(Object obj) {
        this(obj.toString());

        object = obj;
        font = new Font("Arial", 1, 12);
        setFont(font);
        //setPopupMenuVisible(false);

    }

    public synchronized void addMouseListener(MouseListener mouseListener) {
        if (!isRemoved) {
            try {
                String className = "javax.swing.JMenu";
                String methodName = "getMouseListeners";
                Class c = Class.forName(className);
                Method m = c.getMethod(methodName, new Class[]{});
            } catch (Exception exp) {
                if (list == null)
                    list = new ArrayList();
                list.add((MouseListener) mouseListener);
            }
        }

        super.addMouseListener(mouseListener);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public QueryMenu(String s, Color color, Color color1, Font font) {
        super(s);
        // list = new ArrayList();
        flag = false;
        object = null;
        //  UIManager.put("MenuItemUI", "InscriptMenuItemUI");
        bg = color;
        fg = color1;
        this.font = font;
        setFont(font);

        setBackground(color);
        setForeground(color1);
        setContentAreaFilled(false);
        setFocusPainted(false);
        removeListeners();
        addMouseListener(this);
    }


    public QueryMenu(Object obj, Color color, Color color1, Font font) {
        this(obj.toString(), color, color1, font);
        object = obj;
        this.font = font;
        setFont(font);
        //  this.getPopupMenu().setEnabled(false);

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
        dimension.height = 29;//29;
        return dimension;
    }

    public void actionPerformed(ActionEvent actionevent) {
    }


    /* public JMenuItem add(JMenuItem jmenuitem) {
         // return super.add(jmenuitem);
         return super.add(jmenuitem);

     }*/


    public void mouseEntered(MouseEvent mouseevent) {

        QueryMenu querymenu = (QueryMenu) mouseevent.getSource();
        pfg = querymenu.getForeground();
        pbg = querymenu.getBackground();
        querymenu.setBackground(new Color(0xd9d9d9));
        querymenu.setForeground(new Color(0xac5539));


        if (isQMClicked) {

            MenuControlManager.setMenuHasControl(true);
        }


    }

    public void mouseExited(MouseEvent mouseevent) {

        QueryMenu querymenu = (QueryMenu) mouseevent.getSource();
        querymenu.setBackground(pbg);
        querymenu.setForeground(pfg);
        if (isQMClicked) {
            MenuControlManager.setMenuHasControl(false);
        }
    }

    public void mouseClicked(MouseEvent mouseevent) {
        isQMClicked = true;

    }

    public void mousePressed(MouseEvent mouseevent) {

    }

    public void mouseReleased(MouseEvent mouseevent) {

    }

    public boolean isLeaf() {
        return false;
    }

    public String toString() {
        //return "<html><body><font size='11pt' face='arial'><b>" + super.toString() + "<b></font><body></html>";
        return super.toString();
    }

    public Insets getInsets() {
        return new Insets(0, 0, 0, 5);
    }

    static {
        bg = Color.white;
        fg = Color.black;
    }
}
