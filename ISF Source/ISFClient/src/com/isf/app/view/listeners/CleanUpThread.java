package com.isf.app.view.listeners;

import com.isf.app.controllers.MenuControlManager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: kamesh
 * Date: Mar 27, 2004
 * Time: 10:49:57 AM
 * To change this template use Options | File Templates.
 */
public class CleanUpThread extends Thread {
    Logger log = LogManager.getLogger(com.isf.app.view.listeners.CleanUpThread.class);
    public CleanUpThread() {
        start();
    }

    public void run() {

        while (MenuControlManager.getLag() > 0) {
            try {
                MenuControlManager.setLagBy(100);
                sleep(100);

            } catch (Exception exp) {
                log.error(exp);
            }
        }
        if (!MenuControlManager.menuHasControl())
            MenuControlManager.doCleanUp();
        else
            MenuControlManager.resetAwake();

    }
}
