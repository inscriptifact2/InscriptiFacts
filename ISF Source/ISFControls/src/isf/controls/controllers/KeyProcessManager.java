/**
 * Created by IntelliJ IDEA.
 * User: kamesh
 * Date: Jun 11, 2004
 * Time: 11:49:14 PM
 * To change this template use Options | File Templates.
 */
package isf.controls.controllers;

import isf.controls.view.listeners.KeyHandler;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class KeyProcessManager {
    private static KeyProcessManager ourInstance;
    private KeyHandler activeKH;
    private ArrayList handlers;

    public synchronized static KeyProcessManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new KeyProcessManager();

        }
        return ourInstance;
    }

    private KeyProcessManager() {
        handlers = new ArrayList();
    }

    public boolean isKeyStrokeRequired() {
        //     System.out.println("strokestrap");
        boolean required = false;
        if (handlers.size() > 0 && activeKH != null)
            required = activeKH.trapKeys();
        return required;
    }

    public void fireKeyEvent(KeyEvent event) {

        if (handlers.size() > 0 && activeKH != null)
            activeKH.fireKeyEvent(event);
    }

    public void addKeyHandler(KeyHandler keyHandler) {
        this.handlers.add(keyHandler);
        //     System.out.println("listkey added");
    }


    public void setActive(KeyHandler kh) {
        if (handlers.size() > 0 && handlers.contains(kh))
            activeKH = kh;
    }


}

