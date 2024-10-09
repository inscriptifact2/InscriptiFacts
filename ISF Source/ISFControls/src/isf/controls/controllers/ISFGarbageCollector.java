package isf.controls.controllers;

//import com.isf.utils.LogManager;

import isf.common.utils.LogManager;
import org.apache.log4j.Logger;

//import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: kamesh
 * Date: May 8, 2004
 * Time: 6:57:37 PM
 * To change this template use Options | File Templates.
 */
public class ISFGarbageCollector extends Thread {


    private final int interval = 60000;
    // ISFGcListener gcCollector;
    Logger logger = LogManager.getLogger(isf.controls.controllers.ISFGarbageCollector.class);

    public ISFGarbageCollector() {                //ISFGcListener gcmode
        // gcCollector = gcmode;
        setPriority(3);

        start();
    }

    public void run() {
        while (true) {
            try {
                sleep(interval);
                Runtime.getRuntime().gc();
            } catch (InterruptedException ie) {
                logger.error(ie);
            }
        }
    }


}
