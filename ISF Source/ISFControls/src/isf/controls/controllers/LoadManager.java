/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.controls.controllers;

import isf.controls.utils.CursorManager;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.Timer;

/**
 *
 * @author IN030223
 */
public class LoadManager implements ActionListener {

    private static LoadManager manager;
//    private Logger log = LogManager.getLogger(isf.controls.controllers.LoadManager");
    private int current = 1;
    private int total = 1;
    private long maximum = 10000;
    private long actual = 10000;
    private long loaded = 1;
    private long lastLoaded = 1;
    private int count = 0;
    private long startTime;
    private Timer timer;
    private boolean loading = false;
    private boolean fromStandalone = false;
    private String notLoaded = "";

    private LoadManager() {
    }

    public static LoadManager getInstance() {
        if (manager == null) {
            manager = new LoadManager();
        }
        return manager;
    }

    public void updateCount() {
        current++;
        if (current > total) {
            stopAnimation();
        }
    }    //  private  ISFProgressBar bar;
    private JProgressBar bar1;

    public void registerMonitor(JProgressBar monitor) {//ISFProgressBar
        bar1 = monitor;
    }

    public void setMaximum(long max) {
        maximum = max;
    }

    public long getMaximum() {
        return maximum;
    }

     public void setActual(long max) {
        actual = max;
    }

    public long getActual() {
        return actual;
    }

    public void setTotal(int max) {
        total = max;
    }

    public void update(long size) {
        loaded += size;
    }

    public void start() {
        if (bar1 != null) {
            bar1.setVisible(true);
            bar1.setMinimum(0);
            bar1.setMaximum((int) (maximum / 1024));
            startTime = System.currentTimeMillis();
            current = 1;
            loaded = 1;
            lastLoaded = 1;
            count = 0;
            loading = true;

            bar1.setString("Loading Images...");
            timer = new Timer(1000, this);
            timer.setInitialDelay(0);
            timer.setCoalesce(true);
            timer.start();


        }
    }

    public void actionPerformed(ActionEvent arg0) {
        if (!fromStandalone) {
            if (lastLoaded != loaded) {
                lastLoaded = loaded;
                count = 0;
                bar1.setValue((int) (loaded / 1024));
            } else {
                count += 10;
                int value = ((int) lastLoaded / 1024) + count;
                bar1.setValue(value);
            }
        } else {
            int x = bar1.getValue();
            bar1.setValue(++x);
        }
        //log.debug("Loaded:" + bar1.getValue());

    }

    public void stopAnimation() {
        //  int elapsed= (int)((System.currentTimeMillis() - startTime)/1000);
        if (timer != null) {
            timer.stop();

            bar1.setString("Finished Loading..");
            bar1.setMaximum(0);
            bar1.setMinimum(0);
            bar1.setValue(0);
            bar1.setVisible(false);
            loading = false;
            CursorManager.changeCursor(Cursor.DEFAULT_CURSOR);
        }

    }

    public void startLoading(String msg) {
        if (bar1 != null) {
            bar1.setVisible(true);
            bar1.setString(msg);
            try {
                Class types[] = new Class[1];
                types[0] = Boolean.TYPE;
                Object values[] = new Object[1];
                values[0] = new Boolean(true);
                Method method = bar1.getClass().getMethod("setIndeterminate", types);
                method.invoke(bar1, values);

            } catch (Exception exp) {
                bar1.setMinimum(1);
                bar1.setMaximum(100);
                timer = new Timer(1000, this);
                timer.setInitialDelay(0);
                timer.setCoalesce(true);
                timer.start();
            }

        }
    }

    public void stopLoading() {
        if (bar1 != null) {
            bar1.setVisible(false);
            if (timer != null) {
                timer.stop();
            }
            try{
            Class types[] = new Class[1];
                types[0] = Boolean.TYPE;
                Object values[] = new Object[1];
                values[0] = new Boolean(false);
                Method method = bar1.getClass().getMethod("setIndeterminate", types);
                method.invoke(bar1, values);
            }catch(Exception exp){}

        }
    }

    public boolean isLoading() {
        return loading;
    }

    

    /**
     * @return the notLoaded
     */
    public String getNotLoaded() {
        return notLoaded;
    }

    /**
     * @param notLoaded the notLoaded to set
     */
    public void setNotLoaded(String notLoaded) {
        if(notLoaded.endsWith(","))
             this.notLoaded = notLoaded.substring(0, notLoaded.length()-1);
        else
            this.notLoaded = notLoaded;
        String[] list = this.notLoaded.split(",");
       String msg =  list.length > 1 ? " images " : " image ";
       this.notLoaded = msg + this.notLoaded;
    }
}
