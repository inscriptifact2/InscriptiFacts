package com.isf.app.view.controls;

import com.isf.app.models.TreeChild;
import com.isf.app.models.TreeParent;
import com.isf.app.view.gui.ObjectCellRenderer;
import com.isf.app.view.gui.SimpleVerticalLayout;
import com.isf.app.view.listeners.ObjectListListener;
import isf.common.view.gui.TextDivision;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ObjectList extends JScrollPane {

    private JPanel container;
    private ObjectListListener ol;
   // private boolean fireOnHover;
    private ActionListener al;
    private ObjectCellRenderer objectrenderer = null;
//    private ObjectCellRenderer objectrenderer1=null;
    private Dimension dimension = null;

    public ObjectList() {
        super(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        container = new JPanel();
     //   fireOnHover = false;
        setViewportView(container);
        container.setBackground(Color.white);
    }

  /*  public void setFireOnHover(boolean flag) {
        fireOnHover = flag;
    }*/

    public synchronized void setData(Vector vector) {
        int i = vector.size();
        dimension = getSize();//getPreferredSize();
        container.removeAll();
        container.setLayout(new SimpleVerticalLayout());//new GridLayout(i >= 50 ? i : 50, 1, 0, 0));
        //  dimension.height = 30;
        //  dimension.width=600;// -= 18;
        for (int j = 0; j < i; j++) {
            TreeChild treechild = (TreeChild) vector.elementAt(j);
            objectrenderer = new ObjectCellRenderer(treechild, dimension);
            objectrenderer.setPreferredSize(dimension);
            objectrenderer.setIndex(j);
            container.add(objectrenderer);
            if (al != null) {
                objectrenderer.addActionListener(al);
            }
           // objectrenderer.setFireOnHover(fireOnHover);
        }

        /*    if(i < 50)
            {
                //System.out.println("in i<50");
                for(int k = i; k < 50; k++)
                {
                    objectrenderer = new ObjectCellRenderer(new TreeParent(""),dimension);
                    if(al != null)
                    {
                        objectrenderer.addActionListener(al);
                    }
                    objectrenderer.setPreferredSize(dimension);
                    containers.add(objectrenderer);
                    objectrenderer.setFireOnHover(fireOnHover);
                }

            }  */
        validate();
        repaint();
    }

    public void addObjectListListener(ObjectListListener objectlistlistener) {
        ol = objectlistlistener;
        objectrenderer.addObjectListListener(ol);
    }

    public void addActionListener(ActionListener actionlistener) {
        al = actionlistener;
    }

    public Insets getInsets() {
        return new Insets(0, 0, 0, 0);
    }

    public static Vector testData() {
        Vector v = new Vector();
        TreeParent tp = null;
        for (int i = 1; i <= 10; i++) {
            TextDivision td = new TextDivision();
            td.setTextDivisionName("Coulmn-" + i);
            td.setTextDivisionDesc("Desc-" + i);
            td.setTextDivisionMuseumNo("museumNo-" + i);
            tp = new TreeParent(td);
            v.addElement(tp);
        }
        return v;
    }
/*
    PSVM(String args[]) {
        JFrame jframe = new JFrame();
        jframe.getContentPane().setLayout(new BorderLayout(0, 0));
        ObjectList objectlist = new ObjectList();
        //  objectlist.setData(vector);
        //  objectlist.setPreferredSize(new Dimension(250, 30));
        //  jframe.getContentPane().add(objectlist, "West");
        //  objectlist = new ObjectList();
        Vector vd = testData();
        //  System.out.println("testData  :"+vd);
        objectlist.setData(vd);
        objectlist.setPreferredSize(new Dimension(650, 30));
        jframe.getContentPane().add(objectlist, "Center");
        jframe.getContentPane().validate();
        jframe.getContentPane().repaint();
        jframe.setSize(650, 300);
        jframe.setVisible(true);
    }
    */
}
