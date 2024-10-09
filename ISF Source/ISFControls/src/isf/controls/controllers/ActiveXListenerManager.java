package isf.controls.controllers;

import isf.controls.view.listeners.ActiveXListener;

public class ActiveXListenerManager {

    private static ActiveXListener al;

    public ActiveXListenerManager() {
    }
    
    public static void registerActivationListener(ActiveXListener list)
    {
        al = list;
    }

    public static void minimized(String s) {
        if (al == null) {
            return;
        } else {
            al.minimized(s);
            return;
        }
    }

    public static void activated(String s) {
        if (al == null) {
            return;
        } else {
            al.activated(s);
            return;
        }
    }

    public static void deleted(String s) {
        if (al == null) {
            return;
        } else {
            al.deleted(s);
            return;
        }
    }

    public static void setNotification(String s) {
        if (al == null) {
            return;
        } else {
            al.setNotification(s);
            return;
        }
    }
}
