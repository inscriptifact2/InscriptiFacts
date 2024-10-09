/**
 * Created by IntelliJ IDEA.
 * User: kamesh
 * Date: May 8, 2004
 * Time: 8:54:09 PM
 * To change this template use Options | File Templates.
 */
package isf.controls.controllers;

import isf.controls.view.containers.ISFSplitPane;

public class IsfSplitManager {
    private static IsfSplitManager ourInstance;
    private ISFSplitPane verticalSplit, horizontalSplit;

    public synchronized static IsfSplitManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new IsfSplitManager();
        }
        return ourInstance;
    }

    private IsfSplitManager() {
    }

    public void setSplitPane(ISFSplitPane sp, int split) {
        if (split == 0) {
            verticalSplit = sp;
        } else if (split == 1) {
            horizontalSplit = sp;
        }

    }

    public void updateState(int up) {
        if (up == 0) {

            if (verticalSplit != null && verticalSplit.getCurrentPos() > 2) {
                verticalSplit.updateLocation();
                verticalSplit.changeImage();
            }
            if (horizontalSplit != null && horizontalSplit.getCurrentPos() > 2) {
                horizontalSplit.updateLocation();
                horizontalSplit.changeImage();
            }
        } else if (up == 1) {
            if (verticalSplit != null && verticalSplit.getCurrentPos() < 2) {
                verticalSplit.updateLocation();
                verticalSplit.changeImage();
            }
            if (horizontalSplit != null && horizontalSplit.getCurrentPos() < 2) {
                horizontalSplit.updateLocation();
                horizontalSplit.changeImage();
            }
        }
    }
}

