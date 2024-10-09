package com.isf.app.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventDispatcher extends Thread {

    private ActionListener al;
    private ActionEvent ae;

    public EventDispatcher(ActionListener actionlistener, ActionEvent actionevent) {
        al = actionlistener;
        ae = actionevent;
        start();
    }

    public void run() {
        //   System.out.println("al from eventdispa  :"+al+"   ae from ed  :"+ae);
        al.actionPerformed(ae);
    }
}
