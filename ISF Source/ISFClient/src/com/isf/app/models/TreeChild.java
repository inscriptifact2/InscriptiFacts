package com.isf.app.models;

import java.io.Serializable;

public class TreeChild
        implements Serializable {

    public Object object;

    public TreeChild() {
    }

    public TreeChild(Object obj) {
        object = obj;
    }

    public Object getChild() {
        return object;
    }

    public boolean isLeaf() {
        return true;
    }

    public String toString() {
        if (object == null) {
            return "";
        } else {
            return object.toString();
        }
    }
}
