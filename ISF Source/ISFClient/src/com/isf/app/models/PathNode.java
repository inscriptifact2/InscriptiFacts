package com.isf.app.models;

public class PathNode {

    public String name;
    public boolean isSelected;

    public PathNode(String s) {
        isSelected = false;
        name = s;
    }

    public PathNode(String s, boolean flag) {
        this(s);
        isSelected = flag;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof PathNode)) {
            return false;
        } else {
            PathNode pathnode = (PathNode) obj;
            return pathnode.name.equals(name);
        }
    }

    public String toString() {
        return name + " " + isSelected;
    }

    public int hashCode() {
        return name.hashCode();
    }
}
