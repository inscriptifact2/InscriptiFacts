package com.isf.app.view.gui;

import com.isf.app.models.PathNode;
import isf.common.Debug;
import java.util.Enumeration;
import java.util.Hashtable;

public class PathSelectionManager {

    public Hashtable paths;
    public static PathSelectionManager psm = null;

    private PathSelectionManager() {
        paths = new Hashtable();
    }

    public static PathSelectionManager getInstance() {
        if (psm == null) {
            psm = new PathSelectionManager();
        }
        return psm;
    }

    private PathNode[][] getEmptyValues() {
        PathNode apathnode[][] = {
            {
                null, null, null
            }, {
                null, null, null
            }
        };
        return apathnode;
    }

    private PathNode[][] getValue(PathNode pathnode) {
        PathNode apathnode[][] = (PathNode[][]) paths.get(pathnode);
        if (apathnode == null) {
            apathnode = getEmptyValues();
            paths.put(pathnode, apathnode);
        }
        return apathnode;
    }

    private boolean canAccept(PathNode pathnode) {
        if (paths.size() < 2) {
            return true;
        } else {
            return paths.containsKey(pathnode);
        }
    }

    public void push(PathNode apathnode[])
            throws IllegalArgumentException {
        if (apathnode == null) {
            throw new IllegalArgumentException("Argument cannot be null. ");
        }
        if (!canAccept(apathnode[0])) {
            throw new IllegalArgumentException("You cannot select more than two across categories.");
        }
        PathNode apathnode1[][] = getValue(apathnode[0]);
        checkPush(apathnode1, apathnode);
        int i = getRowIndex(apathnode1, apathnode);
        if (i == -1) {
            throw new IllegalArgumentException("You cannot select more than two within a category.");
        }
        for (int j = 1; j < apathnode.length; j++) {
            if (apathnode1[i][j - 1] == null) {
                apathnode1[i][j - 1] = apathnode[j];
            }
        }

        if (isIdentical(apathnode1[0], apathnode1[1])) {
            for (int k = 0; k < apathnode1[1].length; k++) {
                apathnode1[1][k] = null;
            }

        }
        merge(apathnode1);
        if (apathnode1[0][0] == null && apathnode1[1][0] == null) {
            paths.remove(apathnode[0]);
        }
        Debug.debug(" in push");
    }

    private void checkPush(PathNode apathnode[][], PathNode apathnode1[])
            throws IllegalArgumentException {
        boolean flag = true;
        for (int i = 1; i < apathnode1.length; i++) {
            if (apathnode[0][i - 1] == null || apathnode[1][i - 1] == null) {
                break;
            }
            if (!apathnode1[i].equals(apathnode[0][i - 1]) && !apathnode1[i].equals(apathnode[1][i - 1])) {
                throw new IllegalArgumentException("You cannot select more than two within a category.");
            }
        }

    }

    private int getRowIndex(PathNode apathnode[][], PathNode apathnode1[]) {
        if (apathnode[0][0] == null && apathnode[1][0] == null) {
            return 0;
        }
        boolean flag = true;
        for (int i = 0; i < apathnode.length; i++) {
            boolean flag1 = true;
            int j;
            for (j = 1; j < apathnode1.length - 1; j++) {
                flag1 &= apathnode1[j].equals(apathnode[i][j - 1]);
            }

            flag1 &= apathnode[i][j - 1] == null;
            if (flag1) {
                return i;
            }
        }

        if (apathnode[0][0] == null) {
            return 0;
        }
        return apathnode[1][0] != null ? -1 : 1;
    }

    public int getMergeIndex(PathNode apathnode[], PathNode apathnode1[]) {
        int i;
        for (i = 0; i < apathnode.length; i++) {
            if (apathnode[i] == null || apathnode1[i] == null) {
                break;
            }
            if (!apathnode[i].equals(apathnode1[i])) {
                return -1;
            }
        }

        if (i == apathnode.length) {
            return 1;
        }
        if (apathnode[i] == null) {
            return 0;
        }
        return apathnode1[i] != null ? -1 : 1;
    }

    private void merge(PathNode apathnode[][]) {
        int i = getMergeIndex(apathnode[0], apathnode[1]);
        if (i == -1) {
            return;
        }
        int j = i != 0 ? 0 : 1;
        for (int k = 0; k < apathnode[i].length; k++) {
            if (apathnode[i][k] != null && apathnode[i][k].isSelected) {
                apathnode[j][k].isSelected = true;
            }
            apathnode[i][k] = null;
        }

    }

    public boolean pop(PathNode apathnode[]) {
        PathNode apathnode1[] = new PathNode[apathnode.length - 1];
        for (int i = 1; i < apathnode.length; i++) {
            apathnode1[i - 1] = apathnode[i];
        }

        dispArray(apathnode1);
        if (!paths.containsKey(apathnode[0])) {
            return true;
        }
        PathNode apathnode2[][] = (PathNode[][]) paths.get(apathnode[0]);
        if (apathnode.length == 1) {
            paths.remove(apathnode[0]);
            return true;
        }
        Debug.debug("*************Before POP**************");
        dispVal();
        Debug.debug("**************************************");
        for (int j = 0; j < apathnode2.length; j++) {
            if (isEqual(apathnode2[j], apathnode1)) {
                apathnode2[j][apathnode1.length - 1].isSelected = false;
                for (int k = apathnode2[j].length - 1; k >= apathnode1.length; k--) {
                    if (apathnode2[j][k] == null) {
                        continue;
                    }
                    if (apathnode2[j][k].isSelected) {
                        break;
                    }
                    apathnode2[j][k] = null;
                }

            }
        }

        Debug.debug("*************After POP**************");
        dispVal();
        Debug.debug("**************************************");
        if (isIdentical(apathnode2[0], apathnode2[1])) {
            for (int l = 0; l < apathnode2[1].length; l++) {
                if (apathnode2[1][l] != null && apathnode2[1][l].isSelected) {
                    apathnode2[0][l].isSelected = true;
                }
                apathnode2[1][l] = null;
            }

        }
        Debug.debug("*************Before Merge**************");
        dispVal();
        Debug.debug("**************************************");
        merge(apathnode2);
        Debug.debug("*************After Merge**************");
        dispVal();
        Debug.debug("**************************************");
        for (int i1 = 0; i1 < apathnode2.length; i1++) {
            for (int j1 = apathnode2[i1].length - 1; j1 >= 0; j1--) {
                if (apathnode2[i1][j1] == null) {
                    continue;
                }
                if (apathnode2[i1][j1].isSelected) {
                    break;
                }
                apathnode2[i1][j1] = null;
            }

        }

        Debug.debug("*************End of POP**************");
        dispVal();
        Debug.debug("**************************************");
        if (apathnode2[0][0] == null && apathnode2[1][0] == null) {
            paths.remove(apathnode[0]);
            return true;
        } else {
            return false;
        }
    }

    private void dispArray(PathNode apathnode[]) {
        for (int i = 0; i < apathnode.length; i++) {
            Debug.debug("\n");
        }

    }

    public void reset() {
        for (Enumeration enumeration = paths.keys(); enumeration.hasMoreElements(); paths.remove((PathNode) enumeration.nextElement())) {
        }
    }

    private boolean isIdentical(PathNode apathnode[], PathNode apathnode1[]) {
        boolean flag = true;
        for (int i = 0; i < apathnode1.length; i++) {
            if (apathnode1[i] == null && apathnode[i] == null) {
                flag &= true;
            } else if (apathnode1[i] == null || apathnode[i] == null) {
                flag &= false;
            } else {
                flag &= apathnode1[i].equals(apathnode[i]);
            }
        }

        return flag;
    }

    private boolean isEqual(PathNode apathnode[], PathNode apathnode1[]) {
        boolean flag = true;
        for (int i = 0; i < apathnode1.length; i++) {
            if (apathnode1[i] == null && apathnode[i] == null) {
                flag &= true;
            } else if (apathnode1[i] == null || apathnode[i] == null) {
                flag &= false;
            } else {
                flag &= apathnode1[i].equals(apathnode[i]);
            }
        }

        return flag;
    }

    private void dispVal() {
        for (Enumeration enumeration = paths.keys(); enumeration.hasMoreElements();) {
            PathNode pathnode = (PathNode) enumeration.nextElement();
            PathNode apathnode[][] = getValue(pathnode);
            for (int i = 0; i < apathnode.length; i++) {
                for (int j = 0; j < apathnode[i].length; j++) {
                }
                Debug.debug("\n");
            }

        }

        Debug.debug("-------------------------------\n");
    }
/*
    PSVM(String args[]) {
        PathNode apathnode[][] = {
            {
                new PathNode("CORPUS"), new PathNode("ABC", true)
            }, {
                new PathNode("CORPUS"), new PathNode("ABC"), new PathNode("dfe"), new PathNode("xyz")
            }, {
                new PathNode("CORPUS"), new PathNode("ABC"), new PathNode("EFG"), new PathNode("xyzq")
            }, {
                new PathNode("CORPUS"), new PathNode("ABCD")
            }, {
                new PathNode("LANGUAGE"), new PathNode("L1")
            }, {
                new PathNode("LANGUAGE"), new PathNode("ABC")
            }, {
                new PathNode("LANGUAGE"), new PathNode("ABC"), new PathNode("EFG")
            }
        };
        PathSelectionManager pathselectionmanager = new PathSelectionManager();
        for (int i = 0; i < apathnode.length; i++) {
            try {
                pathselectionmanager.push(apathnode[i]);
            } catch (Exception exception) {
                Debug.debug(exception);
            }
        }

        PathNode apathnode1[] = {
            new PathNode("CORPUS"), new PathNode("ABC")
        };
        pathselectionmanager.pop(apathnode1);
        pathselectionmanager.dispVal();
    }
*/
}
