/**
 * Created by IntelliJ IDEA.
 * User: IN030223
 * Date: Dec 29, 2007
 * Time: 12:36:40 PM
 * To change this template use File | Settings | File Templates.
 */
package com.isf.app.utils;

import isf.common.utils.LogManager;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import org.apache.log4j.Logger;

public class GeneralMenuManager {
    private static GeneralMenuManager ourInstance = new GeneralMenuManager();
    static Logger log = LogManager.getLogger(com.isf.app.utils.GeneralMenuManager.class);

    public static boolean isGMBClicked = false;
    private static boolean hasExited = false;

    public static GeneralMenuManager getInstance() {
        return ourInstance;
    }

    private GeneralMenuManager() {
    }

    public static void performExit(JMenuItem menu, boolean exit) {

        hasExited = exit;

        if (menu == null)
            return;

        final JMenuItem comp = menu;
        Runnable r = new Runnable() {

            public void run() {
                JMenu popmenu = null;
                if (comp instanceof JMenu)
                    popmenu = (JMenu) comp;
                else
                    popmenu = (JMenu) ((JPopupMenu) ((JMenuItem) comp).getParent()).getInvoker();
                try {
                    Thread.sleep(500);
                } catch (Exception exp) {
                    log.error(exp);
                }
                if (hasExited) {

                    while (!popmenu.isTopLevelMenu()) {
                        popmenu.setPopupMenuVisible(false);
                        //System.out.println(menu.getParent()+"::"+menu.getActionCommand() );
                        if (popmenu.getParent() instanceof JPopupMenu)
                            popmenu = (JMenu) ((JPopupMenu) popmenu.getParent()).getInvoker();
                    }
                    popmenu.setPopupMenuVisible(false);
                    GeneralMenuManager.isGMBClicked = false;

                }
            }
        };

        Thread thread = new Thread(r);
        thread.start();
    }
}
