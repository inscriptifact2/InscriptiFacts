package com.isf.app.utils;

import isf.controls.controllers.KeyProcessManager;
import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;

/**
 * Created by IntelliJ IDEA.
 * User: kamesh
 * Date: Jun 11, 2004
 * Time: 11:41:33 PM
 * To change this template use Options | File Templates.
 */
public class ISFKeyEventTracker extends
        EventQueue {
    private KeyProcessManager processManager =
            KeyProcessManager.getInstance();

    protected void dispatchEvent(AWTEvent event) {

        try{
        super.dispatchEvent(event);
        }catch(Exception exp){}
        //System.out.println("calledintracker");
        if (event instanceof KeyEvent) {
            if (event.getID() == KeyEvent.KEY_PRESSED || event.getID() == KeyEvent.KEY_RELEASED) {
                if (processManager != null && processManager.isKeyStrokeRequired()) {
                    processManager.fireKeyEvent((KeyEvent) event);
                }
            }
        }


    }
}
