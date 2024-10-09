/**
 * Created by IntelliJ IDEA.
 * User: kamesh
 * Date: May 16, 2004
 * Time: 9:54:12 PM
 * To change this template use Options | File Templates.
 */
package com.isf.app.controllers;

import com.isf.app.view.listeners.ClickListener;


public class ClickControlManager {
    private static ClickControlManager ourInstance;
    private static boolean hasControl = false;
    private static ClickListener cl;
    private static int lag = 50;
    private static int origLag;

    public synchronized static ClickControlManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new ClickControlManager();
        }
        origLag = 50;
        lag = origLag;
        return ourInstance;
    }

    private ClickControlManager() {
    }

    public static boolean divHasControl() {
        return hasControl;
    }

    public static void setDivHasControl(boolean flag) {
        hasControl = flag;
        if (!flag)
            lag = origLag;
    }

    public static void setMenuListener(ClickListener mlist) {
        cl = mlist;
    }

    public static int getLag() {
        // System.out.println("lag:"+lag);
        return lag;
    }

    public static void setLagBy(int i) {
        lag -= i;

    }


    public static void doCleanUp() {
        // System.out.println(hasControl+" "+ awake+" "+lag);
        if (cl != null && !hasControl) {
            //   cl.cleanUp() ;
            //   awake=false;
        }
    }

    public static void pollCleanup() {
        lag = origLag;
        //  if(!awake) {
        //      awake=true;
        //  new ClickThread();

        // }
    }

    public static void resetAwake() {
        //   awake=false;
    }

}

