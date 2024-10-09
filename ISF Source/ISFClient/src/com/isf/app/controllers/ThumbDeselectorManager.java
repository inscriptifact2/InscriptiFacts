/**
 * Created by IntelliJ IDEA.
 * User: kamesh
 * Date: May 31, 2004
 * Time: 10:22:27 PM
 * To change this template use Options | File Templates.
 */
package com.isf.app.controllers;

import com.isf.app.view.listeners.ThumbDeselectorListener;

public class ThumbDeselectorManager {
    private static ThumbDeselectorManager ourInstance;
    private static ThumbDeselectorListener tdl;

    public synchronized static ThumbDeselectorManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new ThumbDeselectorManager();
        }
        return ourInstance;
    }

    private ThumbDeselectorManager() {
    }

    public void addThumbDeselectorListener(ThumbDeselectorListener tdl) {
        this.tdl = tdl;
    }

    public void deleteSelection(String fname) {
        if (tdl != null)
            tdl.deselectSelection(fname);
    }
}

