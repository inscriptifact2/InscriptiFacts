/**
 * Created by IntelliJ IDEA.
 * User: kamesh
 * Date: Mar 27, 2004
 * Time: 10:30:44 AM
 * To change this template use Options | File Templates.
 */
package com.isf.app.controllers;

import com.isf.app.view.listeners.CleanUpThread;
import com.isf.app.view.listeners.MenuListener;
import isf.common.utils.LogManager;
import isf.common.utils.InscriptConfigLoader;
import org.apache.log4j.Logger;

public class MenuControlManager {
    private static MenuControlManager ourInstance;
    private static boolean hasControl = false;
    private static MenuListener ml;
    private static int lag = 1000;
    private static int origLag;
    private static boolean awake = false;
    static Logger log = LogManager.getLogger(com.isf.app.controllers.MenuControlManager.class);

    public synchronized static MenuControlManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new MenuControlManager();
            try {
                origLag = new Integer(InscriptConfigLoader.getInstance().getProperty("lag")).intValue();

            } catch (Exception e) {
                origLag = 1000;
                log.error(e);
            }
        }

        lag = origLag;
        // System.out.println("lag="+lag);
        return ourInstance;
    }

    private MenuControlManager() {
    }

    public static boolean menuHasControl() {
        return hasControl;
    }

    public static void setMenuHasControl(boolean flag) {
        hasControl = flag;
        if (!flag)
            lag = origLag;
    }

    public static void setMenuListener(MenuListener mlist) {
        ml = mlist;
    }

    public static void doCleanUp() {
        // System.out.println(hasControl+" "+ awake+" "+lag);
        if (ml != null && !hasControl) {
            ml.cleanUp();
            awake = false;
        }
    }

    public static void pollCleanup() {
        lag = origLag;
        if (!awake) {
            awake = true;
            new CleanUpThread();

        }
    }

    public static void resetAwake() {
        awake = false;
    }


    public static int getLag() {
        // System.out.println("lag:"+lag);
        return lag;
    }

    public static void setLagBy(int i) {
        lag -= i;

    }
}

