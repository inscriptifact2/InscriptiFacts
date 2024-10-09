package com.isf.app.view.gui;

import com.isf.app.models.TreeChild;
import com.isf.app.view.controls.InscriptDyanBorder;
import com.isf.app.view.listeners.ObjectListListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ObjectRenderer extends JLabel {
    //  private static Color hc = new Color(0xac5539);
    private static Color nc = new Color(0);
    private int width;
    private int height;
    // public boolean isExpand;
    private ObjectListListener ol;
  //  private MouseListener ml = null;
    private ActionListener al = null;
    public boolean inside;
  //  private boolean fireOnHover;
 //   public boolean isPressed;
    private TreeChild child = null;
    private int index;

    public ObjectRenderer(String s, int i, int j) {
        // super("<html><body><table><tr><td cellpadding=5 cellspacing=5><font face='Arial' size='2'  ><b>" + s + "</b></font></td></tr></table></body></html>");
        super(s);
        width = i;
        height = j;
        setBackground(Color.white);
        setForeground(Color.black);
        setIcon(new ImageIcon(s));
        setBorder(new InscriptDyanBorder(new Color(0x808080), 0, 0, 0, 1));
        setFont(new Font("Arial", Font.BOLD, 12));
     //   fireOnHover = false;
        inside = false;
     //   isPressed = false;
        setInside();
        addMouseListener(new Highlight());
    }

 /*   public void setFireOnHover(boolean flag) {
        fireOnHover = flag;
    }

    public boolean getFireOnHover() {
        return fireOnHover;
    }*/

    public void setTreeChild(TreeChild child) {
        this.child = child;
    }

    public TreeChild getTreeChild() {
        return child;
    }

    public void setInside() {
        setForeground(nc);
        updateUI();
        inside = false;
    /*    if (isPressed) {
            isPressed = false;
        }*/
    }

    public void addActionListener(ActionListener al) {
        this.al = al;
    }

 /*   public void addMouseListener(MouseListener ml) {
        this.ml = ml;
    }*/

    public void setSubData() {
        if (al != null) {
            //  setCursor(new Cursor(3));

            new com.isf.app.utils.EventDispatcher(al, new ActionEvent(this, 1, ""));
            // setCursor(new Cursor(0));

        }
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void addObjectListListener(ObjectListListener objectlistlistener) {
        ol = objectlistlistener;
        //   System.out.println("ol from renderer  :"+ol);

    }

    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    public Insets getInsets() {
        //  return new Insets(0,0,0,0);
        return new Insets(5, 5, 5, 5);
    }
    
    class Highlight extends MouseAdapter{
        boolean handled = false;
        @Override
        public void mouseEntered(MouseEvent mouseevent) {
            if(!handled){
                 handled = true;
            }else{
                return;
            }
           
            ObjectRenderer obj = (ObjectRenderer)mouseevent.getSource();
            ObjectCellRenderer parent = (ObjectCellRenderer)obj.getParent();
            parent.processEntry();
            
        }
        @Override
        public void mouseExited(MouseEvent mouseevent){
            if(handled){
                handled = false;
            }else{
                return;
            }
            
            ObjectRenderer obj = (ObjectRenderer)mouseevent.getSource();
            ObjectCellRenderer parent = (ObjectCellRenderer)obj.getParent();
            parent.processExit();
            
        }
        
        @Override
        public void mouseClicked(MouseEvent mouseevent){
            if(handled){
                handled = false;
            }else{
                return;
            }
            
            ObjectRenderer obj = (ObjectRenderer)mouseevent.getSource();
            ObjectCellRenderer parent = (ObjectCellRenderer)obj.getParent();
            parent.handleClick();
        }
    }
    
}
