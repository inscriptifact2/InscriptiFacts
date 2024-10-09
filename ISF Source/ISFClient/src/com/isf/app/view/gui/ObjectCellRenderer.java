package com.isf.app.view.gui;

import com.isf.app.models.TreeChild;
import com.isf.app.models.TreeParent;
import com.isf.app.view.containers.TextDivisionPanel;
import com.isf.app.view.listeners.ObjectListListener;
import isf.common.view.gui.TextDivisionSubDivision;
import isf.controls.view.controls.InscriptBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JPanel;

public class ObjectCellRenderer extends JPanel {
     //   implements MouseListener {

    private static Color hc = new Color(0xac5539);
    private static Color nc = new Color(0);
    private TreeChild child;
    public ActionListener al;
    //  private boolean fireOnHover;
    private ObjectListListener ol;
    public boolean inside;
    private String textDivName = "";
    private String textDesc = "";
    private String museumNo = "";
    private static Dimension dim;
    // private ResolutionManager resolutionManager=null;
    public ObjectRenderer or1 = null;
    private ObjectRenderer or2, or3;
    private int index;

    public ObjectCellRenderer() {
    }

    public ObjectCellRenderer(TreeChild treechild, Dimension dimension) {
        dim = dimension;
        // System.out.println("dim from ocr  :"+dim);
        setLayout(new FlowLayout(0, 0, 0));
        //   addMouseListener(this);
        //  fireOnHover = false;
        child = treechild;
        int i = (int) ((double) (dim.width) * 0.25D);
        if (child instanceof TreeParent) {
            Object obj = child.getChild();
            if (obj.toString() != "") {
                isf.common.view.gui.TextDivision textdivision = (isf.common.view.gui.TextDivision) obj;
                textDivName = textdivision.getTextDivisionName();
                textDesc = textdivision.getTextDivisionDesc();
                museumNo = textdivision.getTextDivisionMuseumNo();
                or1 = new ObjectRenderer(textDivName, i, dim.height + 10);
                or2 = new ObjectRenderer(textDesc, 2 * i, dim.height + 10);
                or3 = new ObjectRenderer(museumNo, dim.width - (3 * i) - 15, dim.height + 10);
                or1.setTreeChild(child);


            } else {
                or1 = new ObjectRenderer(treechild.toString(), (int) ((double) dim.width * 0.25D), dim.height + 10);//dim.width,dim.height);
                or2 = new ObjectRenderer(treechild.toString(), (int) ((double) dim.width * 0.5D), dim.height + 10);
                or3 = new ObjectRenderer(treechild.toString(), (int) ((double) dim.width * 0.25D) - 15, dim.height + 10);



                /*   or1.setFireOnHover(fireOnHover);
                 or2.setFireOnHover(fireOnHover);
                 or3.setFireOnHover(fireOnHover);*/

            }
        } else {
            TextDivisionSubDivision textdivisionsubdivision = (TextDivisionSubDivision) treechild.getChild();
            //s = textdivisionsubdivision.toString();
            or1 = new ObjectRenderer("    " + textdivisionsubdivision.getTextDivisionSubDivisionName(), (int) ((double) dim.width * 0.25D), dim.height + 10);//dim.width,dim.height);
            or2 = new ObjectRenderer("    " + textdivisionsubdivision.getTextDescTitle(), (int) ((double) dim.width * 0.5D), dim.height + 10);
            or3 = new ObjectRenderer("    " + textdivisionsubdivision.getMainTextPubNo(), (int) ((double) dim.width * 0.25D) - 15, dim.height + 10);


        }
     /*   or1.addMouseListener(this);
        or2.addMouseListener(this);
        or3.addMouseListener(this);*/

        add(or1);
        add(or2);
        add(or3);
        //setForeground(nc);
        setBackground(Color.white);
        setBorder(new InscriptBorder(Color.red)); //black
        validate();
        repaint();
    }

    public void setIndex(int index) {
        this.index = index;
        or1.setIndex(index);
        or2.setIndex(index);
        or3.setIndex(index);
    }

    public int getIndex() {
        return index;
    }

    /* public void setFireOnHover(boolean flag) {
     fireOnHover = flag;
     or1.setFireOnHover(fireOnHover);
     or2.setFireOnHover(fireOnHover);
     or3.setFireOnHover(fireOnHover);
     }*/
    public Vector getRenderers() {
        Vector renderers = new Vector();
        renderers.addElement(or1);
        renderers.addElement(or2);
        renderers.addElement(or3);
        return renderers;
    }

    /* public boolean getFireOnHover() {
     return fireOnHover;
     }*/
    public void addActionListener(ActionListener actionlistener) {
        al = actionlistener;
        or1.addActionListener(al);
        or2.addActionListener(al);
        or3.addActionListener(al);

    }

    public void addObjectListListener(ObjectListListener objectlistlistener) {
        ol = objectlistlistener;
        or1.addObjectListListener(ol);
        or2.addObjectListListener(ol);
        or3.addObjectListListener(ol);
    }

    public TreeChild getTreeChild() {
        return child;
    }

    public Insets getInsets() {
        return new Insets(0, 0, 0, 0);//10, 10, 10, 2);
    }

    public void setInside() {
        inside = false;
        or1.setInside();
        or2.setInside();
        or3.setInside();
    }

    public void processEntry() {
    
        setForeground(hc);
        inside = true;
       // System.out.println("cell in mouse entered" + objectrenderer.museumNo + "::" + objectrenderer.textDivName);
        
        or1.inside = true;
        or1.setForeground(hc);
        or1.updateUI();

        or2.inside = true;
        or2.setForeground(hc);
        or2.updateUI();

        or3.inside = true;
        or3.setForeground(hc);
        or3.updateUI();
    }

    public void processExit() {

        if (TextDivisionPanel.current == this) {
            //   System.out.println("current == objRenderer");
            return;
        } else {
            //   System.out.println("current != objRenderer");
            this.setInside();
            return;
        }
    }
    public void handleClick() {
       
        TreeChild treechild = this.getTreeChild();
        if (!treechild.isLeaf() || treechild.isLeaf()) {
            new com.isf.app.utils.ClickThread(this);
           
            return;
        }
        if (treechild.toString().equals("")) {
            //       System.out.println(" .....");
            return;
        }
        if (ol != null) {

            ol.objectSelected(treechild);

        }
    }

  /*  public void mouseClicked(MouseEvent mouseevent) {
        
        //moved to mousepressed for fixing issue with java  8
    }*/

    public void setSubData() {
        or1.setSubData();
    }

  /*  public static void main(String args[]) {
        Dimension dim = new Dimension(600, 30);
        isf.common.view.gui.TextDivision td = new isf.common.view.gui.TextDivision();
        td.setTextDivisionName("Coulmn-1");
        td.setTextDivisionDesc("Desc-1");
        td.setTextDivisionMuseumNo("museumNo-1");
        TreeParent tp = new TreeParent(td);
        ObjectCellRenderer o = new ObjectCellRenderer(tp, dim);

        isf.common.view.gui.TextDivision td1 = new isf.common.view.gui.TextDivision();
        td1.setTextDivisionName("Coulmn-2");
        td1.setTextDivisionDesc("Desc-2");
        td1.setTextDivisionMuseumNo("museumNo-2abcdefghijkl");
        TreeParent tp1 = new TreeParent(td1);
        ObjectCellRenderer ocr = new ObjectCellRenderer(tp1, dim);

        JPanel panel = new JPanel();
        panel.setLayout(new SimpleVerticalLayout());
        panel.add(o);
        panel.add(ocr);

        JFrame fr = new JFrame();
        fr.getContentPane().add(panel, "North");
        fr.setSize(650, 400);
        fr.setVisible(true);
    }*/
}
