package com.isf.app.utils;

import com.isf.app.view.gui.ImageConsumer;
import isf.common.request.RequestProxy;
import isf.common.utils.LogManager;
import javax.swing.ImageIcon;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: kamesh
 * Date: Jan 10, 2004
 * Time: 3:22:38 PM
 * To change this template use Options | File Templates.
 */
public class FetchImageThread extends Thread {
    ImageConsumer parent;
    String details;
    int index = 0;
    Logger log = LogManager.getLogger(com.isf.app.utils.FetchImageThread.class);

    public FetchImageThread(ImageConsumer parent, String str, int ind) {
        this.parent = parent;
        details = str;
        index = ind;
        start();
    }

    public FetchImageThread(ThreadGroup tg, ImageConsumer parent, String str, int ind) {
        super(tg, "thread" + ind);
        this.parent = parent;
        details = str;
        index = ind;
        start();
    }

    public void run() {

        try {
            ImageIcon imageicon = RequestProxy.getRequestDispatcher().fetchImage(details);
            parent.setUpImg(imageicon, index);
        } catch (Exception ie) {
            log.error(ie);
        }
    }
}
