package com.isf.app.utils;

import com.isf.app.view.listeners.ABListener;

public class CatalougeObserver {
    public static ABListener abl;
    public static int count = 0;


    public static void fireUpdateButtons() {
        if (abl == null) {
            return;
        } else {
            abl.fireUpdateButtons();
            return;
        }
    }

    public static void updateCount(boolean b) {
        if (b)
            --count;
        else
            ++count;
        if (count == 0) {
            fireUpdateButtons();
        }
    }

}