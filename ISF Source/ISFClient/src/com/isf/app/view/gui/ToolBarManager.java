package com.isf.app.view.gui;

import isf.common.Debug;

public class ToolBarManager {

    public boolean arr[] = {
        true, false, false, false, false, false, false
    };
    private static ToolBarManager tbm = null;

    private ToolBarManager() {
    }

    public static ToolBarManager getInstance() {
        if (tbm == null) {
            tbm = new ToolBarManager();
        }
        return tbm;
    }

    public void setEnable(int i) {
        arr[i] = true;
        if (i == arr.length - 2) {
            setEnable(arr.length - 1);
        }
    }

    public boolean isEnabled(int i) {
        return arr[i];
    }

    public void setDisable(int i) {
        arr[i] = false;
        if (i == arr.length - 2) {
            setDisable(arr.length - 1);
        }
    }

    public void disableLeft(int i) {
        for (int j = i + 1; j < arr.length; j++) {
            setDisable(j);
        }

    }

    public void reset() {
        arr[0] = true;
        for (int i = 1; i < arr.length; i++) {
            arr[i] = false;
        }

    }

    private void printArray() {
        String s = "";
        for (int i = 0; i < arr.length; i++) {
            System.out.print(s + "(" + i + ".." + arr[i] + ")");
            s = "--->";
        }

        Debug.debug("\n=====================================\n");
    }



}
