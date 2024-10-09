package com.isf.app.models;

import java.io.Serializable;
import java.util.Vector;

public class TreeParent extends TreeChild
        implements Serializable {

    public Vector childs;

    public TreeParent() {
        childs = new Vector();
    }

    public TreeParent(Object obj) {
        super(obj);
        childs = new Vector();
    }

    public void addChild(TreeChild treechild) {
        childs.addElement(treechild);
    }

    public int getChildCount() {
        return childs.size();
    }

    public TreeChild getChildAt(int i) {
        return (TreeChild) childs.elementAt(i);
    }

    public boolean isLeaf() {
        return childs.size() <= 0;
    }

    public Vector getAllChilds() {
        return childs;
    }

    public String toString() {
        return super.toString();
    }

    public TreeChild getChild(Object obj) {
        Object obj1 = null;
        int i = 0;
        for (int j = getChildCount(); i < j; i++) {
            obj1 = getChildAt(i);
            if (((TreeChild) (obj1)).toString().equals(obj.toString())) {
                return ((TreeChild) (obj1));
            }
        }

        obj1 = new TreeParent(obj);
        addChild(((TreeChild) (obj1)));
        return ((TreeChild) (obj1));
    }

    public void addPath(Object aobj[]) {
        if (aobj == null || aobj.length == 0 || aobj[0] == null) {
            return;
        }
        if (aobj[0].toString().trim().length() == 0) {
            return;
        }
        TreeParent treeparent = (TreeParent) getChild(aobj[0].toString().trim());
        if (aobj.length > 1) {
            treeparent.addPath(getTrim(aobj));
        }
    }

    private Object[] getTrim(Object aobj[]) {
        Object aobj1[] = new Object[aobj.length - 1];
        for (int i = 1; i < aobj.length; i++) {
            aobj1[i - 1] = aobj[i];
        }

        return aobj1;
    }

    public void setLeafs() {
        int i = 0;
        for (int j = getChildCount(); i < j; i++) {
            TreeChild treechild = getChildAt(i);
            if (treechild.isLeaf()) {
                childs.remove(treechild);
                addChild(new TreeChild(treechild.toString()));
            } else {
                ((TreeParent) treechild).setLeafs();
            }
        }

    }

    private void printArr(Object aobj[]) {
        System.out.print("[");
        for (int i = 0; i < aobj.length; i++) {
            System.out.print((aobj[i] != null ? aobj[i].toString().trim() : "NULL") + (i != aobj.length - 1 ? "," : ""));
        }

    }

    public void printTree(String s) {
        int i = 0;
        for (int j = getChildCount(); i < j; i++) {
            TreeChild treechild = getChildAt(i);
            if (!treechild.isLeaf()) {
                ((TreeParent) treechild).printTree(s + "  ");
            }
        }

    }

}
